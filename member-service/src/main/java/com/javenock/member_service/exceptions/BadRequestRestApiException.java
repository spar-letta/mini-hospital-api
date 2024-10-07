package com.javenock.member_service.exceptions;

public class BadRequestRestApiException extends Exception {
    public BadRequestRestApiException(String message) {
        super(message);
    }
}
