package com.github.reenatobruno.parts_api.util;

import java.util.Objects;

public class SupplierDomainValidation {

    private SupplierDomainValidation () {}

    public static String normalize(String value) {
        return value != null ? value.strip() : null;
    }

    public static String require_non_blank(String value, String field, int maxLength) {
        Objects.requireNonNull(value, field + " is required");

        if (value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }

        if (value.length() > maxLength) {
            throw new IllegalArgumentException(field + " must not exceed " + maxLength + " characteres");
        }
        return value;
    }
}
