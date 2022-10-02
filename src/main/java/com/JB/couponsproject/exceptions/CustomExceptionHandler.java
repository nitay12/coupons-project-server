package com.JB.couponsproject.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ExceptionDetailsDto appExcpHandler(ApplicationException e) {
        return new ExceptionDetailsDto(e.getMessage());
    }

}
