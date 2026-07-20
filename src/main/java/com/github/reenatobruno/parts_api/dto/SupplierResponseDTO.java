package com.github.reenatobruno.parts_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record SupplierResponseDTO(

        @Schema(description = "Supplier unique ID", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
        UUID supplierId,

        @Schema(description = "CNPJ of the supplier", example = "12345678000195")
        String cnpj,

        @Schema(description = "Legal company name", example = "Bosch do Brasil Ltda")
        String companyName,

        @Schema(description = "Trade name", example = "Bosch Brasil")
        String tradeName,

        @Schema(description = "Contact email", example = "contato@bosch.com.br")
        String email,

        @Schema(description = "Contact phone", example = "11999999999")
        String phone,

        @Schema(description = "Zip code", example = "01310-100")
        String zip,

        @Schema(description = "Street name", example = "Avenida Paulista")
        String street,

        @Schema(description = "Street number", example = "1000A")
        String number,

        @Schema(description = "Address complement", example = "Apto 201 Bloco B")
        String complement,

        @Schema(description = "District", example = "Vila Nova Conceição")
        String district,

        @Schema(description = "City", example = "São Paulo")
        String city,

        @Schema(description = "State", example = "SP")
        String state,

        @Schema(description = "Timestamp when the supplier was created", example = "2024-01-15T10:30:00Z")
        Instant createdAt,

        @Schema(description = "Timestamp when the supplier was last updated", example = "2024-01-15T10:30:00Z")
        Instant updatedAt,

        @Schema(description = "User who created the supplier", example = "bruno@email.com")
        String createdBy,

        @Schema(description = "User who last updated the supplier", example = "bruno@email.com")
        String updatedBy
) {
}
