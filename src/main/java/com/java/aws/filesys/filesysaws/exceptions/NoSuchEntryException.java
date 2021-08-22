package com.java.aws.filesys.filesysaws.exceptions;

public class NoSuchEntryException extends Exception {
    public static final String DEFAULT_EX_MESSAGE = "Couldn't find any data of type %s1 by given criteria: %s2";
    public NoSuchEntryException(String message) {
        super(message);
    }

    public NoSuchEntryException(String message, Throwable cause) {
        super(message, cause);
    }
}
