package com.example.SpringSegurity.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank
        String nombre,

        @Email
        String email,

        @Size(min = 8)
        String password
) {
}