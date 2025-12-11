package com.javaLab.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT)
public class FileUploadException extends RuntimeException {

    public FileUploadException(String message){
        super(message);
    }
}