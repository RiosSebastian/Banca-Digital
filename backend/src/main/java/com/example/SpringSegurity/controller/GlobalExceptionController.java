package com.example.SpringSegurity.controller;

import com.example.SpringSegurity.dto.ErrorDtoRes;
import com.example.SpringSegurity.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionController {

    // Método auxiliar para obtener la hora actual siempre fresca
    private LocalDateTime getCurrentTimestamp() {
        return LocalDateTime.now(ZoneId.of("America/Buenos_Aires"));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDtoRes> handleNotFoundException(NotFoundException exception, HttpServletRequest request) {
        int httpStatus = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(httpStatus).body(new ErrorDtoRes(
                httpStatus,
                request.getMethod(),
                "Elemento no encontrado",
                exception.getMessage(),
                getCurrentTimestamp(),
                null
        ));
    }

    // AHORA SÍ: Agregamos la anotación que faltaba
    @ExceptionHandler(DuplicatedException.class)
    public ResponseEntity<ErrorDtoRes> handleDuplicatedException(DuplicatedException exception, HttpServletRequest request) {
        int httpStatus = HttpStatus.CONFLICT.value();
        return ResponseEntity.status(httpStatus).body(new ErrorDtoRes(
                httpStatus,
                request.getMethod(),
                "Elemento duplicado",
                exception.getMessage(),
                getCurrentTimestamp(),
                null
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDtoRes> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {

        int httpStatus = HttpStatus.BAD_REQUEST.value();
        List<String> details = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    if (error instanceof FieldError fieldError) {
                        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
                    }
                    return error.getDefaultMessage();
                }).toList();

        return ResponseEntity.status(httpStatus).body(new ErrorDtoRes(
                httpStatus,
                request.getMethod(),
                "La request tiene parámetros inválidos o incompletos.",
                "Validación fallida",
                getCurrentTimestamp(),
                details
        ));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDtoRes> handleBadCredentialsException(BadCredentialsException exception, HttpServletRequest request) {
        int httpStatus = HttpStatus.UNAUTHORIZED.value();
        return ResponseEntity.status(httpStatus).body(new ErrorDtoRes(
                httpStatus,
                request.getMethod(),
                "Credenciales inválidas",
                "Usuario o contraseña incorrectos",
                getCurrentTimestamp(),
                null
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDtoRes> handleAllExceptions(Exception exception, HttpServletRequest request) {
        int httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return ResponseEntity.status(httpStatus).body(new ErrorDtoRes(
                httpStatus,
                request.getMethod(),
                "Ocurrió un error inesperado.",
                exception.getMessage(),
                getCurrentTimestamp(),
                null
        ));
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorDtoRes> handleInsufficientBalance(InsufficientBalanceException exception, HttpServletRequest request) {

        int httpStatus = HttpStatus.BAD_REQUEST.value();

        return ResponseEntity.status(httpStatus)
                .body(new ErrorDtoRes(
                        httpStatus,
                        request.getMethod(),
                        "Saldo insuficiente",
                        exception.getMessage(),
                        getCurrentTimestamp(),
                        null
                ));
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ErrorDtoRes> handleInvalidRefreshToken(InvalidRefreshTokenException exception, HttpServletRequest request) {

        int httpStatus = HttpStatus.UNAUTHORIZED.value();

        return ResponseEntity.status(httpStatus)
                .body(new ErrorDtoRes(
                        httpStatus,
                        request.getMethod(),
                        "Token inválido",
                        exception.getMessage(),
                        getCurrentTimestamp(),
                        null
                ));
    }

    @ExceptionHandler(ExpiredRefreshTokenException.class)
    public ResponseEntity<ErrorDtoRes> handleExpiredToken(ExpiredRefreshTokenException exception, HttpServletRequest request) {

        int httpStatus = HttpStatus.UNAUTHORIZED.value();

        return ResponseEntity.status(httpStatus)
                .body(new ErrorDtoRes(
                        httpStatus,
                        request.getMethod(),
                        "Token expirado",
                        exception.getMessage(),
                        getCurrentTimestamp(),
                        null
                ));
    }

    @ExceptionHandler(AccountBlockedException.class)
    public ResponseEntity<ErrorDtoRes> handleAccountBlocked(AccountBlockedException exception, HttpServletRequest request) {

        int httpStatus = HttpStatus.FORBIDDEN.value();

        return ResponseEntity.status(httpStatus)
                .body(new ErrorDtoRes(
                        httpStatus,
                        request.getMethod(),
                        "Cuenta bloqueada",
                        exception.getMessage(),
                        getCurrentTimestamp(),
                        null
                ));
    }

    @ExceptionHandler(DailyLimitExceededException.class)
    public ResponseEntity<ErrorDtoRes> handleDailyLimit(DailyLimitExceededException exception, HttpServletRequest request) {

        int httpStatus = HttpStatus.BAD_REQUEST.value();

        return ResponseEntity.status(httpStatus)
                .body(new ErrorDtoRes(
                        httpStatus,
                        request.getMethod(),
                        "Límite diario excedido",
                        exception.getMessage(),
                        getCurrentTimestamp(),
                        null
                ));
    }

    @ExceptionHandler(InvalidPasswordResetTokenException.class)
    public ResponseEntity<String> handleInvalidPasswordResetToken(InvalidPasswordResetTokenException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ExpiredPasswordResetTokenException.class)
    public ResponseEntity<String> handleExpiredPasswordResetToken(ExpiredPasswordResetTokenException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}