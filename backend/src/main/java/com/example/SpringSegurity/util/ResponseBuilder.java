package com.example.SpringSegurity.util;

import com.example.SpringSegurity.dto.ApiResponse;

import java.time.LocalDateTime;

public class ResponseBuilder {

    public static <T> ApiResponse<T> success(
            String message,
            T data
    ) {

        return new ApiResponse<>(
                true,
                message,
                data,
                LocalDateTime.now()
        );
    }

    public static ApiResponse<Void> success(
            String message
    ) {

        return new ApiResponse<>(
                true,
                message,
                null,
                LocalDateTime.now()
        );
    }

    public static ApiResponse<Void> error(
            String message
    ) {

        return new ApiResponse<>(
                false,
                message,
                null,
                LocalDateTime.now()
        );
    }
}