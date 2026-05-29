package com.example.SpringSegurity.auth.dto.response;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        String type
) {
}