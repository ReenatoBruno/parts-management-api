package com.github.reenatobruno.parts_api.util;

import java.util.Objects;
import java.util.regex.Pattern;

public class UserDomainValidation {

    private UserDomainValidation () {}

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{11}$");

    public static String normalize(String value) {
        return value != null ? value.strip() : null;
    }

    public static String requireNonBlank(String value, String field, int maxLength) {
        Objects.requireNonNull(value, field + " is required");

        if (value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }

        if (value.length() > maxLength) {
            throw new IllegalArgumentException(field + " must not exceed" + maxLength + " characters");
        }
        return value;
    }

    public static String requireCpf(String value, String field, int maxLength) {

        String normalized = requireNonBlank(value, field, maxLength);

        if (!CPF_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException(field + " must contain exactly 11 digits ");
        }
        return normalized;
    }

    public static String requireEmail(String value, String field, int maxLength) {

        String normalized = requireNonBlank(value, field, maxLength);

        if (EMAIL_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException(field + " must be a valid email");
        }
        return normalized;
    }

    public static String filterOnlyDigits(String value) {
        if (value == null) return null;

        return value.replaceAll("\\D", "");
    }

    public static String requireValidPhone(String value, String fieldName, int maxLength) {

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " não pode estar em branco.");
        }

        if (value.length() < 10 || value.length() > 11) {
            throw new IllegalArgumentException(fieldName + " deve conter 10 (Fixo) ou 11 (Celular) dígitos numéricos com DDD.");
        }

        return value;
    }
}
