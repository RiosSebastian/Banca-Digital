package com.example.SpringSegurity.dto.dtoReq;

import com.example.SpringSegurity.entity.UserEntity;

public record AccountDtoReq(String alias,
                            String cbu,
                            Double balance,
                            UserEntity user,
                            UserEntity id) {
}
