package com.example.SpringSegurity.service;

import com.example.SpringSegurity.dto.BalanceHistoryDto;
import com.example.SpringSegurity.dto.RecentTransactionDto;
import com.example.SpringSegurity.dto.TransaccionDtoRes;
import com.example.SpringSegurity.dto.dtoReq.MovimientoDtoReq;
import com.example.SpringSegurity.dto.dtoReq.TransaccionDtoReq;
import com.example.SpringSegurity.entity.AccountEntity;
import com.example.SpringSegurity.util.TipoTransaccion;

import java.time.LocalDateTime;
import java.util.List;

public interface TransaccionService {

    TransaccionDtoRes createMovimiento(MovimientoDtoReq dto);// crear una transaccion

    TransaccionDtoRes realizarTransferencia(TransaccionDtoReq dto);

    TransaccionDtoRes updateTransaccion(Double monto , TipoTransaccion tipo, AccountEntity cuentaDestino);//actualizar transaccion

    void deleteTransaccion(Long id);//eliminar transaccion

    List<TransaccionDtoRes> getAllTransaccion();//listar las transacciones

    List<TransaccionDtoRes>  listTransaccionMonto(Double monto);//listar transacciones por monto

    List<TransaccionDtoRes>listTransaccionfecha(LocalDateTime fecha);//listar transacciones por fecha

    List<TransaccionDtoRes>listTransaccionTipo(TipoTransaccion tipo);//listar transacciones por tipo

    List<TransaccionDtoRes>

    historialCuenta(Long cuentaId);

    Double getMonthlyIncome(Long userId);

    Double getMonthlyExpenses(Long userId);

    List<RecentTransactionDto> getRecentTransactions(Long userId);

    List<BalanceHistoryDto> getBalanceHistory(Long userId);
}
