package com.github.reenatobruno.parts_api.infra;

public class PartNotFoundException extends RuntimeException {
    public PartNotFoundException(Long id) {
        super("Part not found with id: " + id);
    }
}
