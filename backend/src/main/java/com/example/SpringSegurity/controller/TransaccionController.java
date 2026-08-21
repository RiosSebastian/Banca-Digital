package com.example.SpringSegurity.controller;

import com.example.SpringSegurity.dto.TransaccionDtoRes;
import com.example.SpringSegurity.dto.dtoReq.MovimientoDtoReq;
import com.example.SpringSegurity.dto.dtoReq.TransaccionDtoReq;
import com.example.SpringSegurity.entity.UserEntity;
import com.example.SpringSegurity.service.TransaccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<TransaccionDtoRes> movimiento(
            @Valid @RequestBody MovimientoDtoReq dto,
            Authentication authentication) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return ResponseEntity.ok(
                transaccionService.createMovimiento(dto, user.getId())
        );
    }

    // =========================================
    // TRANSFERENCIA
    // =========================================

    @PostMapping("/transferencia")
    public ResponseEntity<TransaccionDtoRes> transferencia(
            @Valid @RequestBody TransaccionDtoReq dto,
            Authentication authentication) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return ResponseEntity.ok(
                transaccionService.realizarTransferencia(dto, user.getId())
        );
    }

    // =========================================
    // LISTADO PAGINADO (usado por la pantalla de Transactions)
    // =========================================

    @GetMapping
    public ResponseEntity<Page<TransaccionDtoRes>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String type,
            Authentication authentication) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return ResponseEntity.ok(
                transaccionService.listarPorUsuario(user.getId(), page, size, type)
        );
    }

    // =========================================
    // HISTORIAL
    // =========================================

    @GetMapping("/{cuentaId}")
    public ResponseEntity<List<TransaccionDtoRes>> historial(
            @PathVariable Long cuentaId,
            Authentication authentication) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return ResponseEntity.ok(
                transaccionService.historialCuenta(cuentaId, user.getId())
        );
    }
}