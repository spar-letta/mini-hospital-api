package com.javenock.doctor_service.exceptions;

public class BadRequestRestApiException extends Exception {
    public BadRequestRestApiException(String message) {
        super(message);
    }
}
