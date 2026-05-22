package com.example.SpringSegurity.exceptions;


public class DuplicatedException extends RuntimeException {
    public DuplicatedException(String message) {
        super(message);
    }
}