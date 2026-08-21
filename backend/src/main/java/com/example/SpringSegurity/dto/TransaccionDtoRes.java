package com.example.SpringSegurity.dto;

import com.example.SpringSegurity.util.TipoTransaccion;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TransaccionDtoRes(
        Long id,
        Double monto,
        LocalDateTime fecha,
        TipoTransaccion tipo,
        String descripcion,
        Long cuentaOrigenId,
        Long cuentaDestinoId
) {
}
