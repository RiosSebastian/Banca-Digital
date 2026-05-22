package com.example.SpringSegurity.dto;

import com.example.SpringSegurity.util.TipoTransaccion;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TransaccionDtoRes(
        LocalDateTime fecha,
                                TipoTransaccion tipo
) {
}
