package com.JB.couponsproject.exceptions;

import com.JB.couponsproject.enums.UserType;

public class UnauthorizedException extends ApplicationException {
    public UnauthorizedException() {
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(UserType userType) {
        super("this action cannot be done by user from type: " + userType.toString());
    }
}
