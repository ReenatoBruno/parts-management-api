package com.github.reenatobruno.parts_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PartRequestDTO {

    @Schema(description = "Unique part identifier containing letters, numbers and hyphens", example = "PROD-001", maxLength = 50)
    @NotBlank(message = "{part.partNumber.notBlank}")
    @Pattern(regexp = "^[A-Za-z0-9\\-]+$", message = "{part.partNumber.pattern}")
    @Size(min = 5, max = 50, message = "{part.partNumber.size}")
    private String partNumber;


    @Schema(description = "Full commercial name of the part", example = "Steel bolt", maxLength = 100)
    @NotBlank(message = "{part.name.notBlank}")
    @Size(min = 5, max = 100, message = "{part.name.size}")
    private String name;

    @Schema(description = "Selling price per unit", example = "9.99", minimum = "0.01")
    @NotNull(message = "{part.price.notNull}")
    @DecimalMin(value = "0.01", message = "{part.price.decimalMin}")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;

    @Schema(description = "Total units available in inventory", example = "10", minimum = "0")
    @NotNull(message = "{part.quantity.notNull}")
    @PositiveOrZero(message = "{part.quantity.positiveOrZero}")
    private Integer quantity;

    @Schema(description = "Legal name of the manufacturing or distributing entity", example = "Steel Parts Inc.", maxLength = 100)
    @NotBlank(message = "{part.supplier.notBlank}")
    @Size(min = 5, max = 100, message = "{part.supplier.size}")
    private String supplier;

    @Schema(description = "Technical specifications and features of the product", example = "Stainless steel bolt 1/4 inch", maxLength = 400)
    @Size(min = 5, max = 500, message = "{part.description.size}")
    private String description;
}
