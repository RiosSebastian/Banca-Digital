package com.example.SpringSegurity.service.servisimpl;

import com.example.SpringSegurity.dto.BalanceHistoryDto;
import com.example.SpringSegurity.dto.RecentTransactionDto;
import com.example.SpringSegurity.dto.TransaccionDtoRes;
import com.example.SpringSegurity.dto.dtoReq.MovimientoDtoReq;
import com.example.SpringSegurity.dto.dtoReq.TransaccionDtoReq;
import com.example.SpringSegurity.entity.AccountEntity;
import com.example.SpringSegurity.entity.TransaccionEntity;
import com.example.SpringSegurity.exceptions.*;
import com.example.SpringSegurity.mapper.TransaccionMapper;
import com.example.SpringSegurity.repository.AccountRepository;
import com.example.SpringSegurity.repository.TransaccionRepository;
import com.example.SpringSegurity.service.TransaccionService;
import com.example.SpringSegurity.util.Estado;
import com.example.SpringSegurity.util.TipoTransaccion;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class TransaccionServiceImpl implements TransaccionService {

    private final AccountRepository accountRepository;

    private final TransaccionRepository transaccionRepository;

    private static final Double DAILY_LIMIT = 500000.0;

    // =====================================================
    // MOVIMIENTOS
    // =====================================================

    @Override
    public TransaccionDtoRes createMovimiento(MovimientoDtoReq dto, Long userId) {
        AccountEntity cuenta = accountRepository.findById(dto.cuentaId())
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));

        validarPropietario(cuenta, userId);
        validarCuentaBloqueada(cuenta);

        switch (dto.tipo()) {

            case DEPOSITO ->
                    realizarDeposito(
                            cuenta,
                            dto.monto()
                    );

            case RETIRO ->
                    realizarRetiro(
                            cuenta,
                            dto.monto()
                    );

            default ->
                    throw new RuntimeException(
                            "Tipo inválido"
                    );
        }

        TransaccionEntity tx = crearTransaccion(dto.monto(), dto.tipo(), cuenta);

        return new TransaccionDtoRes(tx.getFecha(), tx.getTipo());
    }



    // =====================================================
    // TRANSFERENCIA
    // =====================================================

    @Override
    public TransaccionDtoRes realizarTransferencia(TransaccionDtoReq dto, Long userId) {
            AccountEntity cuentaOrigen = accountRepository.findById(dto.cuentaOrigenId())
                    .orElseThrow(() -> new NotFoundException("Cuenta origen no encontrada"));

        validarPropietario(cuentaOrigen, userId);

        validarCuentaBloqueada(cuentaOrigen);

        validarTransferenciaPropia(cuentaOrigen, cuentaDestino);

        validarSaldo(cuentaOrigen, dto.monto());

        validarLimiteDiario(cuentaOrigen, dto.monto());

        // descontar origen
        cuentaOrigen.setBalance(cuentaOrigen.getBalance() - dto.monto());

        // sumar destino
        cuentaDestino.setBalance(cuentaDestino.getBalance() + dto.monto());

        accountRepository.save(cuentaOrigen);

        accountRepository.save(cuentaDestino);

        TransaccionEntity tx = new TransaccionEntity();

        tx.setMonto(dto.monto());

        tx.setFecha(LocalDateTime.now());

        tx.setTipo(TipoTransaccion.TRANSFERENCIA_ENVIADA);

        tx.setCuentaOrigen(cuentaOrigen);

        tx.setCuentaDestino(cuentaDestino);

        tx.setAccount(cuentaOrigen);

        transaccionRepository.save(tx);

        return new TransaccionDtoRes(tx.getFecha(), tx.getTipo());
    }

    // =====================================================
    // HISTORIAL
    // =====================================================

    @Override
    public List<TransaccionDtoRes> historialCuenta(Long cuentaId, Long userId) {
        AccountEntity cuenta = accountRepository.findById(cuentaId)
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));

        validarPropietario(cuenta, userId);

        return transaccionRepository.findAllByCuentaId(cuentaId)
                .stream()
                .map(TransaccionMapper::toDto)
                .toList();
    }




    // =====================================================
    // MÉTODOS PRIVADOS
    // =====================================================

    private void realizarDeposito(AccountEntity cuenta, Double monto) {

        cuenta.setBalance(
                cuenta.getBalance() + monto
        );

        accountRepository.save(cuenta);
    }

    private void realizarRetiro(AccountEntity cuenta, Double monto) {

        validarSaldo(cuenta, monto);

        cuenta.setBalance(
                cuenta.getBalance() - monto
        );

        accountRepository.save(cuenta);
    }

    private void validarSaldo(AccountEntity cuenta, Double monto) {

        if (cuenta.getBalance() < monto) {

            throw new InsufficientBalanceException(
                    "Saldo insuficiente"
            );
        }
    }

    private void validarCuentaBloqueada(AccountEntity cuenta) {

        if (cuenta.getUser().getEstado()
                == Estado.BLOQUEADA) {

            throw new AccountBlockedException(
                    "La cuenta está bloqueada"
            );
        }
    }

    private void validarTransferenciaPropia(AccountEntity origen, AccountEntity destino) {

        if (origen.getId()
                .equals(destino.getId())) {

            throw new RuntimeException(
                    "No puedes transferirte a ti mismo"
            );
        }
    }

    private void validarLimiteDiario(AccountEntity cuenta, Double monto) {

        Double totalHoy =
                transaccionRepository
                        .sumTransfersToday(
                                cuenta.getId()
                        );

        if ((totalHoy + monto)
                > DAILY_LIMIT) {

            throw new DailyLimitExceededException(
                    "Superaste el límite diario permitido"
            );
        }
    }

    private TransaccionEntity crearTransaccion(Double monto, TipoTransaccion tipo, AccountEntity account) {
        TransaccionEntity tx = new TransaccionEntity();
        tx.setMonto(monto);
        tx.setFecha(LocalDateTime.now());
        tx.setTipo(tipo);
        tx.setAccount(account);
        return transaccionRepository.save(tx);
    }

    private void validarPropietario(AccountEntity cuenta, Long userId) {
        if (!cuenta.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("La cuenta no te pertenece");
        }
    }


    @Override
    public TransaccionDtoRes updateTransaccion(Double monto, TipoTransaccion tipo, AccountEntity cuentaDestino) {
        return null;
    }

    @Override
    public void deleteTransaccion(Long id) {

    }

    @Override
    public List<TransaccionDtoRes> getAllTransaccion() {
        return List.of();
    }

    @Override
    public List<TransaccionDtoRes> listTransaccionMonto(Double monto) {
        return List.of();
    }

    @Override
    public List<TransaccionDtoRes> listTransaccionfecha(LocalDateTime fecha) {
        return List.of();
    }

    @Override
    public List<TransaccionDtoRes> listTransaccionTipo(TipoTransaccion tipo) {
        return List.of();
    }

    @Override
    public Double getMonthlyIncome(Long userId) {
        return transaccionRepository.sumMonthlyIncome(userId);
    }

    @Override
    public Double getMonthlyExpenses(Long userId) {
        return transaccionRepository.sumMonthlyExpenses(userId);
    }

    @Override
    public List<RecentTransactionDto> getRecentTransactions(Long userId) {
        return transaccionRepository.findTop5ByAccountUserIdOrderByFechaDesc(userId)
                .stream()
                .map(t -> new RecentTransactionDto(
                        t.getId(),
                        t.getTipo().name(),
                        t.getMonto(),
                        t.getTipo(),
                        t.getFecha()
                ))
                .toList();
    }

    @Override
    public List<BalanceHistoryDto> getBalanceHistory(Long userId) {
        List<TransaccionEntity> transacciones =
                transaccionRepository.findByAccountUserIdOrderByFechaAsc(userId);

        Map<String, Double> balancePorMes = new LinkedHashMap<>();
        double acumulado = 0.0;

        for (TransaccionEntity t : transacciones) {
            acumulado += switch (t.getTipo()) {
                case DEPOSITO, TRANSFERENCIA_RECIBIDA -> t.getMonto();
                case RETIRO, TRANSFERENCIA_ENVIADA -> -t.getMonto();
            };
            String mes = t.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM"));
            balancePorMes.put(mes, acumulado);
        }

        return balancePorMes.entrySet().stream()
                .map(e -> new BalanceHistoryDto(e.getKey(), e.getValue()))
                .toList();
    }


}