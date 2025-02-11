package com.tco.misc;

public class BadRequestException extends Exception {

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }
}