package com.github.reenatobruno.parts_api.exception;

import java.util.UUID;

public class DealerNotFoundException extends RuntimeException {
    public DealerNotFoundException(UUID dealerId) {
        super("Dealer not found with ID: " + dealerId);
    }
}
