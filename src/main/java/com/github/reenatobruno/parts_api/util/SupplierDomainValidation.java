package com.github.reenatobruno.parts_api.util;

import java.util.Objects;
import java.util.regex.Pattern;


public class SupplierDomainValidation {

    private SupplierDomainValidation () {}

    private static final Pattern CNPJ_PATTERN = Pattern.compile("^\\d{14}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public static String normalize(String value) {
        return value != null ? value.strip() : null;
    }

    public static String upperCase(String value) { return value != null ? value.toUpperCase() : null; }

    public static String lowerCase(String value) { return value != null ? value.toLowerCase() : null; }

    public static String requireNonBlank(String value, String field, int maxLength) {
        Objects.requireNonNull(value, field + " is required");

        if (value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }

        if (value.length() > maxLength) {
            throw new IllegalArgumentException(field + " must not exceed " + maxLength + " characteres");
        }
        return value;
    }

    public static String requireCnpj(String value, String field, int maxLength) {

        String normalized = requireNonBlank(value, field, maxLength);

        if (!CNPJ_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException(field + " must contain exactly 14 digits ");
        }
        return normalized;
    }

    public static String requireEmail(String value, String field, int maxLength) {

        String normalized = requireNonBlank(value, field, maxLength);

        if (!EMAIL_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException(field + " must be a valid email");
        }
        return normalized;
    }

    public static String filterOnlyDigits(String value) {
        if (value == null) return null;

        return value.replaceAll("\\D", "");
    }

    public static String sanitizeText(String value) {
        if (value == null) return null;

        return value
                .replaceAll("\\s{2,}", " ")
                .replaceAll("[^\\p{L}\\p{N}\\s\\-/]", "");
    }

    public static String requireNonBlankIfPresent(String value, String fieldName, int maxLength) {
        if (value == null) return null;

        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank if provided");
        }

        if (value.length() > maxLength) {
            throw new IllegalArgumentException(fieldName + " must not exceed " + maxLength + " characters");
        }
        return value;
    }
}
