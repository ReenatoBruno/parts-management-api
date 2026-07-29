package com.github.reenatobruno.parts_api.exception;

public class SupplierAlreadyExistsException extends RuntimeException {
    public SupplierAlreadyExistsException(String cnpj) {
        super("Supplier already exists with CNPJ: " + cnpj);
    }
}
