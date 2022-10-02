package com.JB.couponsproject.exceptions;

import com.JB.couponsproject.enums.UserType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends ApplicationException {
    public ForbiddenException() {
    }

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(UserType userType) {
        super("this action cannot be done by user from type: " + userType.toString());
    }
}
