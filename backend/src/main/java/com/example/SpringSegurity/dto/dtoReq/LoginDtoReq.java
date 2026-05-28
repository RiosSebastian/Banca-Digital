package com.example.SpringSegurity.dto.dtoReq;


public record LoginDtoReq(
        String email,
        String password
) {
}