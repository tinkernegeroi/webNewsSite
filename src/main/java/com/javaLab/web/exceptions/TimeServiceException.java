package com.javaLab.web.exceptions;

public class TimeServiceException extends RuntimeException {
    public TimeServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}