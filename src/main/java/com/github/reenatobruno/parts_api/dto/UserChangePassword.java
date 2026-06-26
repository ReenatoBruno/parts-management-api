package com.github.reenatobruno.parts_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserChangePassword(

        @Schema(description = "Current password for verification", example = "Senha123!")
        @NotBlank
        String currentPassword,

        @Schema(description = "New password, must contain uppercase, lowercase, digit, special character, between 8 and 60 characters long", example = "Senha123!")
        @NotBlank
        @Size(min = 8, max = 60)
        String newPassword

) {
}
