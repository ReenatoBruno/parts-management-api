package com.github.reenatobruno.parts_api.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record PartUpdateDTO (

    @Schema(description = "The name of the product", example = "Steel bolt")
    @NotBlank(message = "{part.name.notBlank}")
    @Size(min = 5, max = 100, message = "{part.name.size}")
    String name,

    @Schema(description = "The price per unit", example = "9.99")
    @NotNull(message = "{part.price.notNull}")
    @DecimalMin(value = "0.01", message = "{part.price.decimalMin}")
    @Digits(integer = 10, fraction = 2)
    BigDecimal price,

    @Schema(description = "Number of units in stock", example = "10")
    @NotNull(message = "{part.quantity.notNull}")
    @PositiveOrZero(message = "{part.quantity.positiveOrZero}")
    Integer quantity,

    @Schema(description = "The name of the supplier", example = "Steel Parts Inc")
    @NotBlank(message = "{part.supplier.notBlank}")
    @Size(min = 5, max = 100, message = "{part.supplier.size}")
    String supplier,

    @Schema(description = "A brief description of the product (optional)" , example = "Stainless steel bolt 1/4 inch")
    @Size(min = 5, max = 500, message = "{part.description.size}")
    String description
) {
}