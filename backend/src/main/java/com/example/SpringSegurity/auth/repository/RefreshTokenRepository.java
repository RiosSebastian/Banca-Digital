package com.example.SpringSegurity.auth.repository;

import com.example.SpringSegurity.auth.entity.RefreshTokenEntity;
import com.example.SpringSegurity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByToken(String token);

    void deleteByUserId(Long userId);

    void deleteByUser(UserEntity user);
}