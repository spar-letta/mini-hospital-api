package com.javenock.gateway_service.advice;

import com.javenock.gateway_service.exception.UnAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AdviceErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnAuthorizedException.class)
    public Map<String, String> handleError(UnAuthorizedException ex){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("System Error :", ex.getMessage());
        return errorMap;
    }
}