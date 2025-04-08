package com.example.SpringSegurity.dto;

public record UserDTORes(Long id,
                         String name,
                         float balance,
                         String email,
                         String userEnum) {
}
