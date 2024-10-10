package com.javenock.gateway_service.exception;

public class UnAuthorizedException extends Exception{
    public UnAuthorizedException(String message) {
        super(message);
    }
}