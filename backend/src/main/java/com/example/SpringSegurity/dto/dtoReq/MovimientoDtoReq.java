package com.example.SpringSegurity.dto.dtoReq;

import com.example.SpringSegurity.util.TipoTransaccion;
import com.example.SpringSegurity.validation.annotation.ValidAmount;

public record MovimientoDtoReq(

        Long cuentaId,

        @ValidAmount
        Double monto,

        TipoTransaccion tipo
) {
}