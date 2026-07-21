package com.github.reenatobruno.parts_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SupplierUpdateDTO(

        @Schema(description = "Legal company name", example = "Bosch Do Brasil Ltda")
        @Size(max = 150, message = "{supplier.companyName.size}")
        String companyName,

        @Schema(description = "Trade name of the supplier", example = "Bosch Brasil")
        @Size(max = 150, message = "{supplier.tradeName.size}")
        String tradeName,

        @Schema(description = "Contact email of the supplier", example = "contato@bosch.com.br")
        @Email(message = "{supplier.email.invalid}")
        @Size(max = 150, message = "{supplier.email.size}")
        String email,

        @Schema(description = "Contact phone number", example = "11999999999")
        @Size(max = 15, message = "{supplier.phone.size}")
        String phone,

        @Schema(description = "Zip code", example = "01310-100")
        @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "{supplier.zip.pattern}")
        @Size(max = 9, message = "{supplier.zip.size}")
        String zip,

        @Schema(description = "Street number", example = "1000A")
        @Pattern(regexp = "^[\\p{L}\\p{N}\\s\\-/]+$", message = "{supplier.number.pattern}")
        @Size(max = 10, message = "{supplier.number.size}")
        String number,

        @Schema(description = "Address complement", example = "Apto 201 Bloco B")
        @Pattern(regexp = "^[\\p{L}\\p{N}\\s\\-/]+$", message = "{supplier.complement.pattern}")
        @Size(max = 50, message = "{supplier.complement.size}")
        String complement
) {
}
