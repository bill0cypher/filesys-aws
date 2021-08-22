package com.java.aws.filesys.filesysaws.exceptions;

public class NotFoundException extends Exception {

    private static final String DEFAULT_EX_MESSAGE = "Couldn't find any data of type: %s";
    public NotFoundException(String type) {
        super(String.format(DEFAULT_EX_MESSAGE, type));
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
