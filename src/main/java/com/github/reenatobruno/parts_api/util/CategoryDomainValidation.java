package com.github.reenatobruno.parts_api.util;

import java.util.Objects;

public class CategoryDomainValidation {

    private CategoryDomainValidation () {}

    public static String normalize(String value) {
        return value != null ? value.strip() : null;
    }

    public static String requireNonBlank(String value, String field, int maxLength) {
        Objects.requireNonNull(value, field + " is required");

        if (value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }

        if (value.length() > maxLength) {
            throw new IllegalArgumentException(field + " must not exceed " + maxLength + " characters");
        }

        return value;
    }

    public static String requireNonBlankIfPresent(String value, String field, int maxLength) {

        if (value == null ) return null;

        if (value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }

        if (value.length() > maxLength) {
            throw new IllegalArgumentException(field + " must not exceed " + maxLength + " characters");

        }
        return  value;
    }
}
