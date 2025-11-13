package com.example.SpringSegurity.dto.dtoReq;

import com.example.SpringSegurity.entity.CuentaEntity;
import com.example.SpringSegurity.util.TipoTransaccion;

import java.time.LocalDateTime;

public record TransaccionDtoReq(Double monto,
                                LocalDateTime fecha,
                                TipoTransaccion tipo,
                                CuentaEntity cuentaOrigen,
                                CuentaEntity cuentaDestino) {
}
