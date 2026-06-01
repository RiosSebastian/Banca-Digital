package com.example.SpringSegurity.auth.dto.request;

import com.example.SpringSegurity.validation.annotation.UniqueEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @UniqueEmail
        @NotBlank
        @Schema(example = "admin@gmail.com")
        String email,

        @NotBlank
        @Schema(example = "Admin123*")
        String password
) {
}