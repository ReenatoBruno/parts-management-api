package com.github.reenatobruno.parts_api.infra;

public class PartNumberAlreadyExistsException extends RuntimeException {
    public PartNumberAlreadyExistsException(String partNumber) {
        super("Part number already exists: " + partNumber);
    }
}
