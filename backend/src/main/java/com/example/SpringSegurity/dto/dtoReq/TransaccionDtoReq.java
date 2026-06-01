package com.example.SpringSegurity.dto.dtoReq;

import com.example.SpringSegurity.util.TipoTransaccion;
import com.example.SpringSegurity.validation.annotation.ValidAmount;

import java.time.LocalDateTime;

public record TransaccionDtoReq(
        Long cuentaId,
        Long cuentaOrigenId,
        Long cuentaDestinoId,
        @ValidAmount
        Double monto,
        TipoTransaccion tipo
) {
}
