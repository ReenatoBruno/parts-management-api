package com.github.reenatobruno.parts_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryUpdateDTO(

        @Schema(description = "Category name", example = "Brakes")
        @NotBlank
        @Size(min = 5, max = 50)
        String categoryName,

        @Schema(description = "Category description", example = "Parts related to the braking system")
        @NotBlank
        @Size(max = 255)
        String categoryDescription

) {
}
