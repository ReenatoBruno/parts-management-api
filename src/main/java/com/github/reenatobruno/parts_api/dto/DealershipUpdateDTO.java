package com.github.reenatobruno.parts_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DealershipUpdateDTO(

        @Schema(description = "Updated legal company name", example = "Autoprime Comercio De Veículos Ltda")
        @Size(max = 150, message = "{dealership.dealerName.size}")
        String dealerName,

        @Schema(description = "Updated trade name", example = "Autoprime Honda")
        @Size(max = 150, message = "{dealership.tradeName.size}")
        String tradeName,

        @Schema(description = "Updated contact email", example = "contato@autoprime.com.br")
        @Email(message = "{dealership.email.invalid}")
        @Size(max = 150, message = "{dealership.email.size}")
        String email,

        @Schema(description = "Updated contact phone", example = "11999999999")
        @Size(max = 15, message = "{dealership.phone.size}")
        String phone,

        @Schema(description = "Updated zip code", example = "01310-100")
        @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "{dealership.zip.pattern}")
        @Size(max = 9, message = "{dealership.zip.size}")
        String zipCode,

        @Schema(description = "Updated street number", example = "1000A")
        @Pattern(regexp = "^[\\p{L}\\p{N}\\s\\-/]+$", message = "{dealership.number.pattern}")
        @Size(max = 10, message = "{dealership.number.size}")
        String number,

        @Schema(description = "Updated address complement", example = "Apto 201 Bloco B")
        @Pattern(regexp = "^[\\p{L}\\p{N}\\s\\-/]+$", message = "{dealership.complement.pattern}")
        @Size(max = 50, message = "{dealership.complement.size}")
        String complement

) {
}
