package com.JB.couponsproject.exceptions;

import com.JB.couponsproject.enums.EntityType;

public class EntityNotFoundException extends ApplicationException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(EntityType entityType, Long id) {
        System.out.println(entityType.name() + " with id: " + id + " not found in the database");
    }
}
