package com.github.reenatobruno.parts_api.exception;

public class UserEmailAlreadyExistsException extends RuntimeException {
    public UserEmailAlreadyExistsException(String userEmail) {
        super("The provided email is already in use: " + userEmail);
    }
}
