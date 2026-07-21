package com.github.reenatobruno.parts_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
public record DealershipResponseDTO(

        @Schema(description = "Registered dealership unique ID", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
        UUID dealerId,

        @Schema(description = "Registered legal company name", example = "Autoprime Comercio de Veículos Ltda")
        String dealerName,

        @Schema(description = "Registered trade name", example = "Autoprime Honda")
        String tradeName,

        @Schema(description = "Registered contact email", example = "contato@autoprime.com.br")
        String email,

        @Schema(description = "Registered contact phone", example = "11999999999")
        String phone,

        @Schema(description = "Registered city", example = "São Paulo")
        String city,

        @Schema(description = "Registered state", example = "SP")
        String state
) {
}
