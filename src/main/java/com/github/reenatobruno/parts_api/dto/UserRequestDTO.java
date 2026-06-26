package com.github.reenatobruno.parts_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record UserRequestDTO(

        @Schema(description = "Full name of the user, max 60 characters", example = "João da Silva")
        @NotBlank
        @Size(max = 60)
        String userName,

        @Schema(description = "CPF of the user, 11 digits, no punctuation", example = "52998224725")
        @NotBlank
        @CPF
        @Size(min = 11, max = 11)
        String userCpf,

        @Schema(description = "Email address of the user, max 150 characters, stored in lowercase", example = "joao.silva@email.com")
        @NotBlank
        @Email
        @Size(max = 150)
        String userEmail,

        @Schema(description = "Password, must contain uppercase, lowercase, digit, special character, between 8 and 60 characters long", example = "Senha123!")
        @NotBlank
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).+$", message = "Password must contain uppercase, lowercase, digit and special character")
        @Size(min = 8, max = 64)
        String userPassword
) {
}
