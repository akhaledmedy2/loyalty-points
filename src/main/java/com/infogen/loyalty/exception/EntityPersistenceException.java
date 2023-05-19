package com.infogen.loyalty.exception;

import javax.persistence.PersistenceException;

public class EntityPersistenceException extends PersistenceException {

    public EntityPersistenceException(String message) {
        super(message);
    }
}