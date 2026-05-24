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

    @Schema(description = "Unique part identifier containing letters, numbers and hyphens", example = "ABC-123")
    @NotBlank(message = "{part.partNumber.notBlank}")
    @Pattern(regexp = "^[A-Za-z0-9\\-]+$", message = "{part.partNumber.pattern}")
    @Size(min = 5, max = 50, message = "{part.partNumber.size}")
    private String partNumber;

    @Schema(description = "Part Name or description label", example = "Steel bolt")
    @NotBlank(message = "{part.name.notBlank}")
    @Size(min = 5, max = 100, message = "{part.name.size}")
    private String name;

    @Schema(description = "Part price in BRL", example = "R$ 9.99")
    @NotNull(message = "{part.price.notNull}")
    @DecimalMin(value = "0.01", message = "{part.price.decimalMin}")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;

    @Schema(description = "Part quantity in stock", example = "10")
    @NotNull(message = "{part.quantity.notNull}")
    @PositiveOrZero(message = "{part.quantity.positiveOrZero}")
    private Integer quantity;

    @Schema(description = "Name of the company or person supplying the part", example = "Steel Parts Inc")
    @NotBlank(message = "{part.supplier.notBlank}")
    @Size(min = 5, max = 100, message = "{part.supplier.size}")
    private String supplier;

    @Schema(description = "Part description (optional)" , example = "Stainless steel bolt 1/4 inch")
    @Size(min = 5, max = 500, message = "{part.description.size}")
    private String description;
}
