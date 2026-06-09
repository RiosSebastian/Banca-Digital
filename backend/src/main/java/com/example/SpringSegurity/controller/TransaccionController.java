package com.example.SpringSegurity.controller;

import com.example.SpringSegurity.dto.TransaccionDtoRes;
import com.example.SpringSegurity.dto.dtoReq.MovimientoDtoReq;
import com.example.SpringSegurity.dto.dtoReq.TransaccionDtoReq;
import com.example.SpringSegurity.service.TransaccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacciones")
@RequiredArgsConstructor
public class TransaccionController {

    private final TransaccionService transaccionService;

    // =========================================
    // DEPOSITO / RETIRO
    // =========================================

    @PostMapping("/movimiento")
    public ResponseEntity<TransaccionDtoRes>
    movimiento(

            @Valid
            @RequestBody MovimientoDtoReq dto
    ) {

        return ResponseEntity.ok(
                transaccionService
                        .createMovimiento(dto)
        );
    }

    // =========================================
    // TRANSFERENCIA
    // =========================================

    @PostMapping("/transferencia")
    public ResponseEntity<TransaccionDtoRes>
    transferencia(

            @Valid
            @RequestBody
            TransaccionDtoReq dto
    ) {

        return ResponseEntity.ok(
                transaccionService
                        .realizarTransferencia(dto)
        );
    }

    // =========================================
    // HISTORIAL
    // =========================================

    @GetMapping("/{cuentaId}")
    public ResponseEntity<List<TransaccionDtoRes>>
    historial(

            @PathVariable Long cuentaId
    ) {

        return ResponseEntity.ok(
                transaccionService
                        .historialCuenta(cuentaId)
        );
    }
}