package com.github.reenatobruno.parts_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record CategoryResponseDTO(

        @Schema(description = "Category unique ID", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
        UUID categoryId,

        @Schema(description = "Category name", example = "Engine")
        String categoryName,

        @Schema(description = "Category description", example = "Parts related to the engine system")
        String categoryDescription,

        @Schema(description = "Timestamp when the category was created", example = "2024-01-15T10:30:00Z")
        Instant createdAt,

        @Schema(description = "Timestamp when the category was last updated", example = "2024-01-15T10:30:00Z")
        Instant updatedAt,

        @Schema(description = "User who created the category")
        String createdBy,

        @Schema(description = "User who last updated the category")
        String updatedBy
) {
}
