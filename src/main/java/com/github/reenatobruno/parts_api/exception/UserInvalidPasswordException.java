package com.github.reenatobruno.parts_api.exception;

public class UserInvalidPasswordException extends RuntimeException {
    public UserInvalidPasswordException() {
        super("Current password is incorrect");
    }
}
