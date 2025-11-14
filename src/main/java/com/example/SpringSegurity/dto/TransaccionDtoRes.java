package com.example.SpringSegurity.dto;

import com.example.SpringSegurity.util.TipoTransaccion;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TransaccionDtoRes(Double monto,
                                LocalDateTime fecha,
                                TipoTransaccion tipo,
                                CuentaEntity cuentaOrigen,
                                CuentaEntity cuentaDestino) {
}
