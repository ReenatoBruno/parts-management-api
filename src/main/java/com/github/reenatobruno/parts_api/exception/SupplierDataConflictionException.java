package com.github.reenatobruno.parts_api.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class SupplierDataConflictionException extends RuntimeException {
    public SupplierDataConflictionException(String message, DataIntegrityViolationException e) {
        super(message);
    }
}
