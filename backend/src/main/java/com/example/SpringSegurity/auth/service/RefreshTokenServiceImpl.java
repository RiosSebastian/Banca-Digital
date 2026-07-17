package com.example.SpringSegurity.auth.service;

import com.example.SpringSegurity.auth.entity.RefreshTokenEntity;
import com.example.SpringSegurity.auth.repository.RefreshTokenRepository;
import com.example.SpringSegurity.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final Long refreshTokenDuration =
            7L * 24 * 60 * 60;

    @Override
    public RefreshTokenEntity createRefreshToken(UserEntity user) {

        RefreshTokenEntity refreshToken =
                RefreshTokenEntity.builder()
                        .user(user)
                        .token(UUID.randomUUID().toString())
                        .expirationDate(
                                LocalDateTime.now()
                                        .plusSeconds(refreshTokenDuration)
                        )
                        .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {

        if (token.getExpirationDate().isBefore(LocalDateTime.now())) {

            refreshTokenRepository.delete(token);

            throw new RuntimeException(
                    "Refresh token expirado"
            );
        }

        return token;
    }

    @Override
    public RefreshTokenEntity findByToken(String token) {

        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Refresh token inválido"));
    }
}