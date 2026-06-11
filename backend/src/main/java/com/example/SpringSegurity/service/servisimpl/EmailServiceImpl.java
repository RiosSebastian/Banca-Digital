package com.example.SpringSegurity.service.servisimpl;

import com.example.SpringSegurity.auth.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl
        implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(
            String to,
            String token
    ) {

        String link =
                "http://localhost:8080/api/auth/verify?token="
                        + token;

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(to);

        message.setSubject(
                "Verificación de cuenta"
        );

        message.setText("""
                Bienvenido a Digital Bank
                
                Verifica tu cuenta:
                
                %s
                """.formatted(link));

        mailSender.send(message);
    }
}
