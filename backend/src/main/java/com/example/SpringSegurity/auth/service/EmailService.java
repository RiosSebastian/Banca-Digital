package com.example.SpringSegurity.auth.service;

public interface EmailService {

    void sendVerificationEmail(
            String to,
            String token
    );
}