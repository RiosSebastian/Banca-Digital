package com.example.SpringSegurity.auth.dto.response;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken,
        String type
) {
}