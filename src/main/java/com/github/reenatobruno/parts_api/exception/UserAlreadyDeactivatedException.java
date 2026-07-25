package com.github.reenatobruno.parts_api.exception;

public class UserAlreadyDeactivatedException extends RuntimeException {
    public UserAlreadyDeactivatedException() {
        super("User account is already deactivated");
    }
}
