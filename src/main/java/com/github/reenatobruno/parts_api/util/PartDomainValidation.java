package com.github.reenatobruno.parts_api.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class PartDomainValidation {

    private PartDomainValidation () {}

    public static String requireNonBlank(String value, String fieldName, int maxLength) {
        Objects.requireNonNull(value, fieldName + " is required");

        String cleaned = value.strip();

        if (cleaned.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }

        if (cleaned.length() > maxLength) {
            throw new IllegalArgumentException(fieldName + " must not exceed " + maxLength + " characters");
        }
        return cleaned;
    }

    public static String requireValidPartNumber(String value, String fieldName, int maxLength) {
        String cleaned = requireNonBlank(value, fieldName, maxLength);

        if (!cleaned.matches("^[A-Za-z0-9\\-]+$")) {
            throw new IllegalArgumentException(fieldName + " must contain only letters and numbers");
        }
        return cleaned.toUpperCase();
    }

    public static BigDecimal requirePositivePrice(BigDecimal value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " is required");

        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(fieldName + " must be greater than zero");
        }
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    public static Integer requirePositiveQuantity(Integer value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " is required");

        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " must be zero or greater");
        }
        return value;
    }

    public static String requireNonBlankIfPresent(String value, String fieldName, int maxLength) {
        if (value == null) return null;

        String cleaned = value.strip();

        if (cleaned.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank if provided");
        }

        if (cleaned.length() > maxLength) {
            throw new IllegalArgumentException(fieldName + " must not exceed " + maxLength + " characters");
        }
        return cleaned;
    }
}
