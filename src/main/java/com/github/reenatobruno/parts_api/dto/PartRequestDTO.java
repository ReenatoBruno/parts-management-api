package com.github.reenatobruno.parts_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record PartRequestDTO (

        @Schema(description = "Category ID that the part belongs to", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
        @NotNull(message = "{part.categoryId.notNull}")
        UUID categoryId,

        @Schema(description = "Unique part identifier containing letters, numbers and hyphens", example = "PROD-001", maxLength = 50)
        @NotBlank(message = "{part.partNumber.notBlank}")
        @Pattern(regexp = "^[A-Za-z0-9\\-]+$", message = "{part.partNumber.pattern}")
        @Size(min = 5, max = 50, message = "{part.partNumber.size}")
        String partNumber,

        @Schema(description = "Full commercial name of the part", example = "Steel bolt", maxLength = 100)
        @NotBlank(message = "{part.name.notBlank}")
        @Size(min = 5, max = 100, message = "{part.name.size}")
        String partName,

        @Schema(description = "Selling price per unit", example = "9.99", minimum = "0.01")
        @NotNull(message = "{part.price.notNull}")
        @DecimalMin(value = "0.01", message = "{part.price.decimalMin}")
        @Digits(integer = 10, fraction = 2)
        BigDecimal price,

        @Schema(description = "Total units available in inventory", example = "10", minimum = "0")
        @NotNull(message = "{part.quantity.notNull}")
        @PositiveOrZero(message = "{part.quantity.positiveOrZero}")
        Integer quantity,

        @Schema(description = "Legal name of the manufacturing or distributing entity", example = "Steel Parts Inc.", maxLength = 100)
        @NotBlank(message = "{part.supplier.notBlank}")
        UUID supplierId,

        @Schema(description = "Technical specifications and features of the product", example = "Stainless steel bolt 1/4 inch", maxLength = 255)
        @Size(max = 255, message = "{part.description.size}")
        String description
) {
}

