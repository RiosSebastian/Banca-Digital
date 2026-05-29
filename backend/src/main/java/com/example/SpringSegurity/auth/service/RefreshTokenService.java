package com.example.SpringSegurity.auth.service;

import com.example.SpringSegurity.auth.entity.RefreshTokenEntity;
import com.example.SpringSegurity.entity.UserEntity;

public interface RefreshTokenService {

    RefreshTokenEntity createRefreshToken(UserEntity user);

    RefreshTokenEntity verifyExpiration(RefreshTokenEntity token);

    RefreshTokenEntity findByToken(String token);
}