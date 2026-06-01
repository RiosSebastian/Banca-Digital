package com.example.SpringSegurity.auth.dto.request;

import com.example.SpringSegurity.validation.annotation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.example.SpringSegurity.validation.annotation.StrongPassword;

public record RegisterRequest(

        @NotBlank
        String nombre,

        @UniqueEmail
        String email,

        @StrongPassword
        String password
) {
}