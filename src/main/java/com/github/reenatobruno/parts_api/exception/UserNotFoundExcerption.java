package com.github.reenatobruno.parts_api.exception;

import java.util.UUID;

public class UserNotFoundExcerption extends RuntimeException {
    public UserNotFoundExcerption(UUID userId) {
        super("User not found with ID: " + userId);
    }
}
