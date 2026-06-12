package com.example.SpringSegurity.exceptions;

public class ExpiredPasswordResetTokenException extends RuntimeException {

    public ExpiredPasswordResetTokenException(
            String message
    ) {
        super(message);
    }
}
