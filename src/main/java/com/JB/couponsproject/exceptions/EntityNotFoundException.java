package com.JB.couponsproject.exceptions;

import com.JB.couponsproject.enums.EntityType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends ApplicationException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(EntityType entityType, Long id) {
        System.out.println(entityType.name() + " with id: " + id + " not found in the database");
    }
}
