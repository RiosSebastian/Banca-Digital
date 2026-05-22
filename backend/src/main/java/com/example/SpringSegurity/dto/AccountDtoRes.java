package com.example.SpringSegurity.dto;

import com.example.SpringSegurity.entity.AccountEntity;
import com.example.SpringSegurity.entity.UserEntity;
import lombok.Builder;

import java.util.List;

@Builder
public record AccountDtoRes(String alias,
                            String cbu,
                            Double balance,
                            UserEntity user
                            ) {
}
