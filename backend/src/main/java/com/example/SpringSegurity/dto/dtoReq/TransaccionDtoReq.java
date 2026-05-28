package com.example.SpringSegurity.dto.dtoReq;

import com.example.SpringSegurity.util.TipoTransaccion;

import java.time.LocalDateTime;

public record TransaccionDtoReq(
        Long cuentaId,
        Long cuentaOrigenId,
        Long cuentaDestinoId,
        Double monto,
        TipoTransaccion tipo
) {
}
