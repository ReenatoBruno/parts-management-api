package com.github.reenatobruno.parts_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
public record PartResponseDTO (

    @Schema(description = "The product's unique ID", example = "1")
    Long id,

    @Schema(description = "The product's unique code or SKU", example = "PROD-001")
    String partNumber,

    @Schema(description = "The name of the product", example = "Steel bolt")
    String name,

    @Schema(description = "The price per unit", example = "9.99")
    BigDecimal price,

    @Schema(description = "Number of units in stock", example = "10")
    Integer quantity,

    @Schema(description = "Name of the company or person supplying the part", example = "Steel Parts Inc")
    String supplier,

    @Schema(description = "A brief description of the product (optional)" , example = "Stainless steel bolt 1/4 inch")
    String description,

    @Schema(description = "Timestamp when the product was created", example = "2024-01-15T10:30:00")
    Instant createdAt,

    @Schema(description = "Timestamp when the product was last updated", example = "2023-03-15T11:45:00")
    Instant updatedAt
) {
}
