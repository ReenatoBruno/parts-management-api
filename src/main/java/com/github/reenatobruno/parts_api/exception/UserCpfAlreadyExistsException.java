package com.github.reenatobruno.parts_api.exception;

public class UserCpfAlreadyExistsException extends RuntimeException {
    public UserCpfAlreadyExistsException(String userCpf) {
        super("User already registered with CPF: " + userCpf);
    }
}
