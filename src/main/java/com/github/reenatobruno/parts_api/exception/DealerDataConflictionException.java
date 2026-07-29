package com.github.reenatobruno.parts_api.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class DealerDataConflictionException extends RuntimeException {
    public DealerDataConflictionException(String message, DataIntegrityViolationException e) {
        super(message);
    }
}
