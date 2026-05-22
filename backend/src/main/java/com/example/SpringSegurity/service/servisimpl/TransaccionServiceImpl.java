package com.example.SpringSegurity.service.servisimpl;

import com.example.SpringSegurity.dto.TransaccionDtoRes;
import com.example.SpringSegurity.dto.dtoReq.TransaccionDtoReq;
import com.example.SpringSegurity.entity.AccountEntity;
import com.example.SpringSegurity.entity.TransaccionEntity;
import com.example.SpringSegurity.exceptions.NotFoundException;
import com.example.SpringSegurity.repository.AccountRepository;
import com.example.SpringSegurity.repository.TransaccionRepository;
import com.example.SpringSegurity.service.TransaccionService;
import com.example.SpringSegurity.util.TipoTransaccion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransaccionServiceImpl implements TransaccionService {

    private final AccountRepository accountRepository;
    private final TransaccionRepository transaccionRepository;

    @Override
    public TransaccionDtoRes createTransaccion(TransaccionDtoReq dto) {

        AccountEntity cuenta = accountRepository.findById(dto.cuentaId())
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));

        if (dto.tipo() == TipoTransaccion.RETIRO && cuenta.getBalance() < dto.monto()) {
            throw new RuntimeException("Saldo insuficiente");
        }

        if (dto.tipo() == TipoTransaccion.RETIRO) {
            cuenta.setBalance(cuenta.getBalance() - dto.monto());
        } else {
            cuenta.setBalance(cuenta.getBalance() + dto.monto());
        }

        TransaccionEntity tx = new TransaccionEntity();
        tx.setMonto(dto.monto());
        tx.setFecha(LocalDateTime.now());
        tx.setTipo(dto.tipo());
        tx.setAccount(cuenta);

        accountRepository.save(cuenta);
        transaccionRepository.save(tx);

        return new TransaccionDtoRes(tx.getFecha(), tx.getTipo());
    }
}