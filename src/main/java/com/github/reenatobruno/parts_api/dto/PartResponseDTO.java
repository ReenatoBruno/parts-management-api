package com.github.reenatobruno.parts_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
public class PartResponseDTO {

    @Schema(description = "The product's unique ID", example = "1")
    private Long id;

    @Schema(description = "The product's unique code or SKU", example = "PROD-001")
    private String partNumber;

    @Schema(description = "The name of the product", example = "Steel bolt")
    private String name;

    @Schema(description = "The price per unit", example = "9.99")
    private BigDecimal price;

    @Schema(description = "Number of units in stock", example = "10")
    private Integer quantity;

    @Schema(description = "Name of the company or person supplying the part", example = "Steel Parts Inc")
    private String supplier;

    @Schema(description = "A brief description of the product (optional)" , example = "Stainless steel bolt 1/4 inch")
    private String description;

    @Schema(description = "Timestamp when the product was created", example = "2024-01-15T10:30:00")
    private Instant createdAt;

    @Schema(description = "Timestamp when the product was last updated", example = "2023-03-15T11:45:00")
    private Instant updatedAt;
}
