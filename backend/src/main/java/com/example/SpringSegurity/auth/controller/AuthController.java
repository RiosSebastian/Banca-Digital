package com.example.SpringSegurity.auth.controller;

import com.example.SpringSegurity.auth.dto.request.LoginRequest;
import com.example.SpringSegurity.auth.dto.request.RefreshTokenRequest;
import com.example.SpringSegurity.auth.dto.request.RegisterRequest;
import com.example.SpringSegurity.auth.dto.response.LoginResponse;
import com.example.SpringSegurity.auth.dto.response.RefreshTokenResponse;
import com.example.SpringSegurity.auth.entity.RefreshTokenEntity;
import com.example.SpringSegurity.auth.service.AuthService;
import com.example.SpringSegurity.auth.service.RefreshTokenService;
import com.example.SpringSegurity.dto.UserDTORes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {

        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTORes> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(request));
    }
    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(
            @RequestBody RefreshTokenRequest request
    ) {

        RefreshTokenEntity refreshToken =
                refreshTokenService.findByToken(
                        request.refreshToken()
                );

        refreshTokenService.verifyExpiration(refreshToken);

        String accessToken =
                jwtUtil.generateToken(
                        refreshToken.getUser().getEmail()
                );

        return ResponseEntity.ok(
                new RefreshTokenResponse(
                        accessToken,
                        refreshToken.getToken(),
                        "Bearer"
                )
        );
    }
}