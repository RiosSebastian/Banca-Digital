package com.example.SpringSegurity.auth.mail;

public interface EmailService {

    void sendVerificationEmail(
            String to,
            String token
    );

    void sendPasswordResetEmail(
            String to,
            String token
    );
}