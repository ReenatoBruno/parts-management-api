package com.github.reenatobruno.parts_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(

        @Schema(description = "Full name associated with the user account", example = "João da Silva")
        @NotBlank
        @Size(max = 60)
        String userName,

        @Schema(description = "Email address associated with the user account", example = "joao.silva@email.com")
        @NotBlank
        @Email
        @Size(max = 150)
        String userEmail
) {
}
