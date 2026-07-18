package com.example.SpringSegurity.dto.dtoReq;

import com.example.SpringSegurity.validation.annotation.ValidAmount;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record TransaccionDtoReq(
        Long cuentaOrigenId,
        @NotBlank
        String cbuDestino,
        @ValidAmount
        Double monto
) {}