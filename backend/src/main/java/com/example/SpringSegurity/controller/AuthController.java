package com.example.SpringSegurity.controller;

import com.example.SpringSegurity.dto.LoginDtoRes;
import com.example.SpringSegurity.dto.dtoReq.LoginDtoReq;
import com.example.SpringSegurity.service.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginDtoRes> login(
            @RequestBody LoginDtoReq req
    ) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.email(),
                        req.password()
                )
        );

        String token = jwtUtil.generateToken(req.email());

        return ResponseEntity.ok(
                new LoginDtoRes(token)
        );
    }
}