package com.github.reenatobruno.parts_api.exception;

public class DealerAlreadyExistsException extends RuntimeException {
    public DealerAlreadyExistsException(String cnpj) {
        super("Dealer already exists with CNPJ:" + cnpj);
    }
}
