package com.github.reenatobruno.parts_api.exception;

import java.util.UUID;

public class SupplierNotFoundException extends RuntimeException {
    public SupplierNotFoundException(UUID supplierId) {
        super("Supplier not found with ID: " + supplierId);
    }
}
