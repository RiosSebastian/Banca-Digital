package com.example.SpringSegurity.dto;

import com.example.SpringSegurity.entity.UserEntity;
import lombok.Builder;

@Builder
public record AccountDtoRes(String alias,
                            String cbu,
                            Double balance,
                            UserEntity user
                            ) {
}
