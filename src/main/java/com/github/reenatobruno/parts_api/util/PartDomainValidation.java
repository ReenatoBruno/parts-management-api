package com.github.reenatobruno.parts_api.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class PartDomainValidation {


    private PartDomainValidation() {}

    public static String requireNonBlank(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " is required");
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value.strip();
    }

    public static String requireNonBlankOrNull(String value, String fieldName) {
        if (value == null) return null;
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank if provided");
        }
        return value.strip();
    }

    public static BigDecimal requirePositivePrice(BigDecimal value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " must not be null");
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(fieldName + " must be greater than zero");
        }
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    public static Integer requirePositiveOrZero(Integer value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " must not be null");
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " must be positive or zero");
        }
        return value;
    }
}

