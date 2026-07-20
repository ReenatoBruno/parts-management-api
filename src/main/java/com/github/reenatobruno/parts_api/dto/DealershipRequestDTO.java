package com.github.reenatobruno.parts_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

public record DealershipRequestDTO(

        @Schema(description = "CNPJ of the dealership", example = "12345678000195")
        @NotBlank(message = "{dealership.cnpj.notBlank}")
        @CNPJ(message = "{dealership.cnpj.invalid}")
        @Size(max = 14, message = "{dealership.cnpj.size}")
        String cnpj,

        @Schema(description = "Legal company name", example = "Autoprime Comercio De Veiculos Ltda")
        @NotBlank(message = "{dealership.companyName.notBlank}")
        @Size(max = 150, message = "{dealership.companyName.size}")
        String companyName,

        @Schema(description = "Trade name of the dealership", example = "Autoprime Honda")
        @NotBlank(message = "{dealership.tradeName.notBlank}")
        @Size(max = 150, message = "{dealership.tradeName.size}")
        String tradeName,

        @Schema(description = "Contact email of the dealership", example = "contato@autoprime.com.br")
        @NotBlank(message = "{dealership.email.notBlank}")
        @Email(message = "{dealership.email.invalid}")
        @Size(max = 150, message = "{dealership.email.size}")
        String email,

        @Schema(description = "Contact phone number", example = "11999999999")
        @NotBlank(message = "{dealership.phone.notBlank}")
        @Size(max = 15, message = "{dealership.phone.size}")
        String phone,

        @Schema(description = "Zip code", example = "01310-100")
        @NotBlank(message = "{dealership.zip.notBlank}")
        @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "{dealership.zip.pattern}")
        @Size(max = 9, message = "{dealership.zip.size}")
        String zip,

        @Schema(description = "Street number", example = "1000A")
        @NotBlank(message = "{dealership.number.notBlank}")
        @Pattern(regexp = "^[\\p{L}\\p{N}\\s\\-/]+$", message = "{dealership.number.pattern}")
        @Size(max = 10, message = "{dealership.number.size}")
        String number,

        @Schema(description = "Address complement", example = "Apto 201 Bloco B")
        @Pattern(regexp = "^[\\p{L}\\p{N}\\s\\-/]+$", message = "{dealership.complement.pattern}")
        @Size(max = 50, message = "{dealership.complement.size}")
        String complement



) {
}
