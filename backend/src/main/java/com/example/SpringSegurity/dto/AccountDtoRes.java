package com.example.SpringSegurity.dto;

import com.example.SpringSegurity.entity.AccountEntity;
import com.example.SpringSegurity.entity.UserEntity;
import com.example.SpringSegurity.util.TipoCuenta;
import lombok.Builder;

import java.util.List;

@Builder
public record AccountDtoRes(
        Long id,
        String alias,
        String cbu,
        Double balance,
        TipoCuenta tipo,
        Long userId
) {}