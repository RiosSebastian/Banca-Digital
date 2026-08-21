package com.example.SpringSegurity.mapper;

import com.example.SpringSegurity.dto.TransaccionDtoRes;
import com.example.SpringSegurity.entity.TransaccionEntity;

public class TransaccionMapper {

    public static TransaccionDtoRes toDto(TransaccionEntity tx){
        return new TransaccionDtoRes(
                tx.getId(),
                tx.getMonto(),
                tx.getFecha(),
                tx.getTipo(),
                descripcion(tx),
                tx.getCuentaOrigen() != null ? tx.getCuentaOrigen().getId() : null,
                tx.getCuentaDestino() != null ? tx.getCuentaDestino().getId() : null
        );
    }

    private static String descripcion(TransaccionEntity tx) {
        return switch (tx.getTipo()) {
            case DEPOSITO -> "Depósito";
            case RETIRO -> "Retiro";
            case TRANSFERENCIA_ENVIADA -> "Transferencia enviada"
                    + (tx.getCuentaDestino() != null ? " a " + tx.getCuentaDestino().getAlias() : "");
            case TRANSFERENCIA_RECIBIDA -> "Transferencia recibida"
                    + (tx.getCuentaOrigen() != null ? " de " + tx.getCuentaOrigen().getAlias() : "");
        };
    }
}
