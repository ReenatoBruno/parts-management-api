package com.github.reenatobruno.parts_api.exception;

import java.util.UUID;

public class PartNotFoundException extends RuntimeException {
    public PartNotFoundException(UUID partId) {
        super("Part not found with id: " + partId);
    }
}
