package com.java.aws.filesys.filesysaws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.java.aws.filesys.filesysaws.config.AWSConfig;
import com.java.aws.filesys.filesysaws.config.props.AWSProperties;
import com.java.aws.filesys.filesysaws.exceptions.BadRequestException;
import com.java.aws.filesys.filesysaws.exceptions.NoSuchEntryException;
import com.java.aws.filesys.filesysaws.model.Event;
import com.java.aws.filesys.filesysaws.model.File;
import com.java.aws.filesys.filesysaws.model.enums.EventType;
import com.java.aws.filesys.filesysaws.model.enums.Status;
import com.java.aws.filesys.filesysaws.repo.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final EventService eventService;
    private final AWSConfig awsConfig;
    private final AWSProperties properties;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public FileService(FileRepository fileRepository, EventService eventService, AWSConfig awsConfig, AWSProperties properties) {
        this.fileRepository = fileRepository;
        this.eventService = eventService;
        this.awsConfig = awsConfig;
        this.properties = properties;
    }

    @Transactional
    public File save(File file) {
        AmazonS3 client = awsConfig.getClient();
        ObjectMetadata metadata = new ObjectMetadata();
        if (null != file.getMetadata()) {
            file.getMetadata().forEach(metadata::addUserMetadata);
        }

        Event event = new Event();
        event.setEventType(EventType.UPLOAD);
        event.setUser(file.getUser());
        event.setModifiedDate(new Date(new java.util.Date().getTime()));
        file = fileRepository.save(file);
        event.setFile(file);
        try {
            eventService.save(event);
        } catch (BadRequestException e) {
            e.printStackTrace();
        }
        client.putObject(properties.getAppBucket(), file.getFilePath(), file.getContent(), metadata);
        return file;
    }

    @Transactional
    public boolean delete(Integer userId, String fileName) throws NoSuchEntryException, BadRequestException {
        List<Event> events = new ArrayList<>();
        List<File> files = fileRepository.findByUserId(userId).stream().filter(file -> file.getFilename().equals(fileName)).collect(Collectors.toList());
        files.forEach(file -> {
            file.setStatus(Status.DELETED);
            file.setLastModifiedDate(new Date(new java.util.Date().getTime()));
            Event event = new Event();
            event.setEventType(EventType.DELETE);
            event.setFile(file);
            event.setUser(file.getUser());
            event.setModifiedDate(new Date(new java.util.Date().getTime()));
            events.add(event);
            awsConfig.getClient().deleteObject(properties.getAppBucket(), file.getFilePath());
        });
        updateAll(files);
        eventService.saveAll(events);
        return true;
    }

    @Transactional
    public List<File> updateAll(List<File> files) {
        files.forEach(file -> entityManager.merge(file));
        return files;
    }

    @Transactional
    public List<File> findUserFiles(Integer id) {
        return fileRepository.findByUserId(id);
    }
}
