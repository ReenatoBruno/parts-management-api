package com.github.reenatobruno.parts_api.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class UserDataConflictionException extends RuntimeException {
    public UserDataConflictionException(String message, DataIntegrityViolationException e) {
        super(message);
    }
}
