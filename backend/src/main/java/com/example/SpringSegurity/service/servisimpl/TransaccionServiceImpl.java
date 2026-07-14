package com.example.SpringSegurity.service.servisimpl;

import com.example.SpringSegurity.dto.BalanceHistoryDto;
import com.example.SpringSegurity.dto.RecentTransactionDto;
import com.example.SpringSegurity.dto.TransaccionDtoRes;
import com.example.SpringSegurity.dto.dtoReq.MovimientoDtoReq;
import com.example.SpringSegurity.dto.dtoReq.TransaccionDtoReq;
import com.example.SpringSegurity.entity.AccountEntity;
import com.example.SpringSegurity.entity.TransaccionEntity;
import com.example.SpringSegurity.exceptions.AccountBlockedException;
import com.example.SpringSegurity.exceptions.DailyLimitExceededException;
import com.example.SpringSegurity.exceptions.InsufficientBalanceException;
import com.example.SpringSegurity.exceptions.NotFoundException;
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
import java.util.List;

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
    public TransaccionDtoRes createMovimiento(MovimientoDtoReq dto) {

        AccountEntity cuenta = accountRepository.findById(dto.cuentaId())
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Cuenta no encontrada"
                                )
                        );

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
    public TransaccionDtoRes realizarTransferencia(TransaccionDtoReq dto) {

        AccountEntity cuentaOrigen = accountRepository.findById(dto.cuentaOrigenId())
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Cuenta origen no encontrada"
                                )
                        );

        AccountEntity cuentaDestino = accountRepository.findById(dto.cuentaDestinoId())
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Cuenta destino no encontrada"
                                )
                        );

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
    public List<TransaccionDtoRes> historialCuenta(Long cuentaId) {

        return transaccionRepository
                .findByCuentaOrigenId(cuentaId)
                .stream()
                .map(TransaccionMapper::toDto)
                .toList();
    }

    @Override
    public Double getMonthlyIncome(Long userId) {
        return 0.0;
    }

    @Override
    public Double getMonthlyExpenses(Long userId) {
        return 0.0;
    }

    @Override
    public List<RecentTransactionDto> getRecentTransactions(Long userId) {
        return List.of();
    }

    @Override
    public List<BalanceHistoryDto> getBalanceHistory(Long userId) {
        return List.of();
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

    private TransaccionEntity crearTransaccion(Double monto, TipoTransaccion tipo, AccountEntity account, AccountEntity origen, AccountEntity destino){

        TransaccionEntity tx = new TransaccionEntity();

        tx.setMonto(monto);
        tx.setFecha(LocalDateTime.now());

        tx.setTipo(tipo);

        tx.setAccount(account);

        tx.setCuentaOrigen(origen);

        tx.setCuentaDestino(destino);

        return transaccionRepository.save(tx);
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
}