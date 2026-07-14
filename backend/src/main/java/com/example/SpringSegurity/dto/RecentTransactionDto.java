package com.example.SpringSegurity.dto;

import com.example.SpringSegurity.util.TipoTransaccion;

import java.time.LocalDateTime;

public record RecentTransactionDto(

        Long id,

        String description,

        Double amount,

        TipoTransaccion type,

        LocalDateTime createdAt

) {
}