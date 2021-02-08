package com.vizsga.gymcs.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends Exception {

    private HttpStatus httpStatus;

    public ValidationException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public ValidationException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}