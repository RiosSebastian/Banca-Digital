package com.example.SpringSegurity.auth.service;

import com.example.SpringSegurity.auth.dto.request.LoginRequest;
import com.example.SpringSegurity.auth.dto.request.RegisterRequest;
import com.example.SpringSegurity.auth.dto.response.LoginResponse;

import com.example.SpringSegurity.auth.entity.RefreshTokenEntity;
import com.example.SpringSegurity.dto.UserDTORes;
import com.example.SpringSegurity.entity.UserEntity;
import com.example.SpringSegurity.mapper.UserMapper;
import com.example.SpringSegurity.repository.UserRepository;

import com.example.SpringSegurity.security.JwtUtil;
import com.example.SpringSegurity.util.UserEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RefreshTokenService refreshTokenService;

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        UserEntity user = userRepository
                .findByEmail(request.email())
                .orElseThrow();

        String accessToken =
                jwtUtil.generateToken(user.getEmail());

        RefreshTokenEntity refreshToken =
                refreshTokenService.createRefreshToken(user);

        return new LoginResponse(
                accessToken,
                refreshToken.getToken(),
                "Bearer"
        );
    }

    @Override
    public UserDTORes register(RegisterRequest request) {

        UserEntity user = UserEntity.builder()
                .nombre(request.nombre())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .userEnum(UserEnum.USER)
                .build();

        userRepository.save(user);

        return userMapper.toDTO(user);
    }
}