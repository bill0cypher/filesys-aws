package com.java.aws.filesys.filesysaws.controller;

import com.java.aws.filesys.filesysaws.exceptions.NoSuchEntryException;
import com.java.aws.filesys.filesysaws.model.Event;
import com.java.aws.filesys.filesysaws.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/events")
public class EventController {

    private final EventService eventService;
    private final Logger logger = LoggerFactory.getLogger(EventController.class.getName());

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(path = "/history/{user_id}")
    public ResponseEntity<List<Event>> recieveHistory(@PathVariable("user_id") Integer id) {
        try {
            logger.info("REST request to receive user history");
            return new ResponseEntity<>(eventService.getUserHistory(id), HttpStatus.OK);
        } catch (NoSuchEntryException e) {
            logger.error(String.format("REST request failed. User by id: %s doesn't exist.", id));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
