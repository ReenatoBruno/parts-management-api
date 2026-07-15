package com.github.reenatobruno.parts_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SupplierUpdateDTO(

        @Schema(description = "Legal company name", example = "Bosch Do Brasil Ltda")
        @NotBlank(message = "{supplier.companyName.notBlank}")
        @Size(max = 150, message = "{supplier.companyName.size}")
        String companyName,

        @Schema(description = "Trade name of the supplier", example = "Bosch Brasil")
        @NotBlank(message = "{supplier.tradeName.notBlank}")
        @Size(max = 150, message = "{supplier.tradeName.size}")
        String tradeName,

        @Schema(description = "Contact email of the supplier", example = "contato@bosch.com.br")
        @NotBlank(message = "{supplier.email.notBlank}")
        @Email(message = "{supplier.email.invalid}")
        @Size(max = 150, message = "{supplier.email.size}")
        String email,

        @Schema(description = "Contact phone number", example = "11999999999")
        @NotBlank(message = "{supplier.phone.notBlank}")
        @Size(max = 15, message = "{supplier.phone.size}")
        String phone,

        @Schema(description = "Zip code", example = "01310-100")
        @NotBlank(message = "{supplier.zip.notBlank}")
        @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "{supplier.zip.pattern}")
        @Size(max = 9, message = "{supplier.zip.size}")
        String zip,

        @Schema(description = "Street name", example = "Avenida Paulista")
        @NotBlank(message = "{supplier.street.notBlank}")
        @Size(max = 150, message = "{supplier.street.size}")
        String street,

        @Schema(description = "Street number", example = "1000A")
        @NotBlank(message = "{supplier.number.notBlank}")
        @Pattern(regexp = "^[\\p{L}\\p{N}\\s\\-/]+$", message = "{supplier.number.pattern}")
        @Size(max = 10, message = "{supplier.number.size}")
        String number,

        @Schema(description = "Address complement", example = "Apto 201 Bloco B")
        @Pattern(regexp = "^[\\p{L}\\p{N}\\s\\-/]+$", message = "{supplier.complement.pattern}")
        @Size(max = 50, message = "{supplier.complement.size}")
        String complement,

        @Schema(description = "District or neighborhood", example = "Vila Nova Conceição")
        @NotBlank(message = "{supplier.district.notBlank}")
        @Size(max = 50, message = "{supplier.district.size}")
        String district,

        @Schema(description = "City name", example = "São Paulo")
        @NotBlank(message = "{supplier.city.notBlank}")
        @Size(max = 50, message = "{supplier.city.size}")
        String city,

        @Schema(description = "State abbreviation", example = "SP")
        @NotBlank(message = "{supplier.state.notBlank}")
        @Size(min = 2, max = 2, message = "{supplier.state.size}")
        String state
) {
}
