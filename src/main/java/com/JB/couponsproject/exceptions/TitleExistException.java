package com.JB.couponsproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TitleExistException extends ApplicationException {
    public TitleExistException(String message) {
        super(message);
    }
}
