package com.java.aws.filesys.filesysaws.util;


import com.java.aws.filesys.filesysaws.exceptions.BadRequestException;
import com.java.aws.filesys.filesysaws.model.File;
import com.java.aws.filesys.filesysaws.model.User;
import com.java.aws.filesys.filesysaws.model.enums.Status;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class FileAdapter {
    private final MultipartFile file;
    private final User user;

    public FileAdapter(MultipartFile file, User user) {
        this.file = file;
        this.user = user;
    }

    public File convertMultipartToFile() throws BadRequestException {
        if (file.isEmpty()) throw new BadRequestException(BadRequestException.DEFAULT_EX_MESSAGE);
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        String fileName = file.getOriginalFilename();
        String path = user.getUsername().concat(java.io.File.separator).concat(null != fileName ? fileName : "unnamed");

        File mappedFile = new File();
        mappedFile.setFilename(fileName);
        mappedFile.setFilePath(path);
        mappedFile.setUser(user);
        mappedFile.setCreated(new Date(new java.util.Date().getTime()));
        mappedFile.setLastModifiedDate(new Date(new java.util.Date().getTime()));
        mappedFile.setStatus(Status.ACTIVE);
        try {
            mappedFile.setContent(file.getInputStream());
            mappedFile.setMetadata(metadata);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mappedFile;
    }
}
