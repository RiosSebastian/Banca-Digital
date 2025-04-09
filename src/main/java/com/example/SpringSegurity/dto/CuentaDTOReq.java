package com.example.SpringSegurity.dto;

import com.example.SpringSegurity.util.Estado;
import com.example.SpringSegurity.util.TipoTransaccion;

public record CuentaDTOReq (String numero,
                           TipoTransaccion tipo,
                           Double saldo,
                           Estado estado){
}
