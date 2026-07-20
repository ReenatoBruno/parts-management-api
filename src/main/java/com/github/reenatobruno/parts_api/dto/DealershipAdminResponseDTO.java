package com.github.reenatobruno.parts_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public record DealershipAdminResponseDTO(

        @Schema(description = "Basic dealership information")
        DealershipResponseDTO dealershipResponseDTO,

        @Schema(description = "Registered CNPJ of the dealership", example = "12345678000195")
        String cnpj,

        @Schema(description = "Registered zip code", example = "01310-100")
        String zip,

        @Schema(description = "Registered street name", example = "Avenida Paulista")
        String street,

        @Schema(description = "Registered street number", example = "1000A")
        String number,

        @Schema(description = "Registered address complement", example = "Apto 201 Bloco B")
        String complement,

        @Schema(description = "Registered district", example = "Vila Nova Conceição")
        String district,

        @Schema(description = "Timestamp when the dealership was created", example = "2024-01-15T10:30:00Z")
        Instant createdAt,

        @Schema(description = "Timestamp when the dealership was last updated", example = "2024-01-15T10:30:00Z")
        Instant updatedAt,

        @Schema(description = "User who created the dealership", example = "bruno@email.com")
        String createdBy,

        @Schema(description = "User who last updated the dealership", example = "bruno@email.com")
        String updatedBy
) {
}
