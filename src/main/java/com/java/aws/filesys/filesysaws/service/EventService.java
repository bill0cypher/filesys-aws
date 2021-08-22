package com.java.aws.filesys.filesysaws.service;

import com.java.aws.filesys.filesysaws.exceptions.BadRequestException;
import com.java.aws.filesys.filesysaws.exceptions.NoSuchEntryException;
import com.java.aws.filesys.filesysaws.model.Event;
import com.java.aws.filesys.filesysaws.repo.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event save(Event event) throws BadRequestException {
        if (null == event) throw new BadRequestException(BadRequestException.DEFAULT_EX_MESSAGE);
        return eventRepository.save(event);
    }

    public List<Event> saveAll(List<Event> events) {
        return eventRepository.saveAll(events);
    }

    public List<Event> getUserHistory(Integer id) throws NoSuchEntryException {
        return Optional.ofNullable(eventRepository.getAllByUserId(id)).orElseThrow(() -> new NoSuchEntryException(NoSuchEntryException.DEFAULT_EX_MESSAGE));
    }

    public boolean deleteUserHistory(Integer id) {
        return entityManager.createQuery("delete from Event e where e.user.id=:user_id")
                .setParameter("user_id", id).executeUpdate() >= 1;
    }
}
