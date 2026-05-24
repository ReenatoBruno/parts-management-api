package com.github.reenatobruno.parts_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
public class PartResponseDTO {

    @Schema(description = "Part ID", example = "1")
    private Long id;

    @Schema(description = "Unique part identifier containing letters, numbers and hyphens", example = "ABC-123")
    private String partNumber;

    @Schema(description = "Part Name or description label", example = "Steel bolt")
    private String name;

    @Schema(description = "Part price in BRL", example = "R$ 9.99")
    private BigDecimal price;

    @Schema(description = "Part quantity in stock", example = "10")
    private Integer quantity;

    @Schema(description = "Name of the company or person supplying the part", example = "Steel Parts Inc")
    private String supplier;

    @Schema(description = "Part description (optional)" , example = "Stainless steel bolt 1/4 inch")
    private String description;

    @Schema(description = "Date when part was created", example = "2024-01-15T10:30:00")
    private Instant createdAt;

    @Schema(description = "Date when part was last updated", example = "2024-01-15T10:30:00")
    private Instant updatedAt;
}
