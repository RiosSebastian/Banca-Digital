package com.example.SpringSegurity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(

        boolean success,

        String message,

        T data,

        LocalDateTime timestamp
) {
}