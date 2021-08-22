package com.java.aws.filesys.filesysaws.controller;

import com.java.aws.filesys.filesysaws.exceptions.BadRequestException;
import com.java.aws.filesys.filesysaws.exceptions.JWTAuthenticationException;
import com.java.aws.filesys.filesysaws.exceptions.NoSuchEntryException;
import com.java.aws.filesys.filesysaws.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlingControllerAdvice {
    protected Logger logger;

    public ExceptionHandlingControllerAdvice() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Requested data wasn't found")
    @ExceptionHandler({NotFoundException.class, NoSuchEntryException.class})
    public ResponseEntity<Object> handleNotFound() {
        logger.error("REST request failed. Requested data wasn't found");
        return new ResponseEntity<>(Map.of(
                "timestamp", LocalDateTime.now(),
                "message", "No entries found."), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad data provided to requested resource")
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequest() {
        logger.error("REST request failed. Invalid data provided.");
        return new ResponseEntity<>(Map.of(
                "timestamp", LocalDateTime.now(),
                "message", "Invalid data provided."), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You're not authorized to reach requested resource")
    @ExceptionHandler(JWTAuthenticationException.class)
    public ResponseEntity<Object> handleForbidden() {
        logger.error("REST request failed. Not authorized.");
        return new ResponseEntity<>(Map.of(
                "timestamp", LocalDateTime.now(),
                "message", "Not authorized to reach resource."
        ), HttpStatus.FORBIDDEN);
    }

}
