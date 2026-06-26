package com.github.reenatobruno.parts_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record UserResponseDTO(

        @Schema(description = "Unique identifier of the user account", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
        UUID userId,

        @Schema(description = "Full name associated with the user account", example = "João da Silva")
        String userName,

        @Schema(description = "Email address associated with the user account", example = "joao.silva@email.com")
        String userEmail,

        @Schema(description = "Date and time when the user account was created", example = "2026-06-21T14:30:00Z")
        Instant createdAt,

        @Schema(description = "Date and time when the user account was last updated", example = "2026-06-21T14:30:00Z")
        Instant updatedAt
) {
}
