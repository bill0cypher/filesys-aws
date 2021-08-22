package com.java.aws.filesys.filesysaws.exceptions;

import java.util.Optional;

public class BadRequestException extends Exception {

    public static final String DEFAULT_EX_MESSAGE = "Bad data provided";
    public BadRequestException(String message) {
        super(message.isBlank() ? DEFAULT_EX_MESSAGE : message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
