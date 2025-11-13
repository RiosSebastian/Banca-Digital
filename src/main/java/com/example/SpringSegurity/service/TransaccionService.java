package com.example.SpringSegurity.service;

import com.example.SpringSegurity.dto.TransaccionDtoRes;
import com.example.SpringSegurity.dto.dtoReq.TransaccionDtoReq;
import com.example.SpringSegurity.entity.CuentaEntity;
import com.example.SpringSegurity.util.TipoTransaccion;

import java.time.LocalDateTime;
import java.util.List;

public interface TransaccionService {

TransaccionDtoRes createTransaccion (TransaccionDtoReq transaccionDtoReq);// crear una transaccion
TransaccionDtoRes updateTransaccion(Double monto , TipoTransaccion tipo, CuentaEntity cuentaDestino);//actualizar transaccion
void deleteTransaccion(Long id);//eliminar transaccion
List<TransaccionDtoRes> getAllTransaccion();//listar las transacciones
List<TransaccionDtoRes>  listTransaccionMonto(Double monto);//listar transacciones por monto

List<TransaccionDtoRes>listTransaccionfecha(LocalDateTime fecha);//listar transacciones por fecha
List<TransaccionDtoRes>listTransaccionTipo(TipoTransaccion tipo);//listar transacciones por tipo
}
