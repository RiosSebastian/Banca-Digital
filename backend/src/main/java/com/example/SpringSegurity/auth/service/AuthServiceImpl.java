package com.example.SpringSegurity.auth.service;

import com.example.SpringSegurity.auth.dto.request.ForgotPasswordRequest;
import com.example.SpringSegurity.auth.dto.request.LoginRequest;
import com.example.SpringSegurity.auth.dto.request.RegisterRequest;
import com.example.SpringSegurity.auth.dto.request.ResetPasswordRequest;
import com.example.SpringSegurity.auth.dto.response.LoginResponse;

import com.example.SpringSegurity.auth.entity.PasswordResetTokenEntity;
import com.example.SpringSegurity.auth.entity.RefreshTokenEntity;
import com.example.SpringSegurity.auth.entity.VerificationTokenEntity;
import com.example.SpringSegurity.auth.mail.EmailService;
import com.example.SpringSegurity.auth.repository.PasswordResetTokenRepository;
import com.example.SpringSegurity.auth.repository.VerificationTokenRepository;
import com.example.SpringSegurity.dto.UserDTORes;
import com.example.SpringSegurity.entity.UserEntity;
import com.example.SpringSegurity.exceptions.AccountBlockedException;
import com.example.SpringSegurity.exceptions.ExpiredPasswordResetTokenException;
import com.example.SpringSegurity.exceptions.InvalidPasswordResetTokenException;
import com.example.SpringSegurity.exceptions.UnauthorizedException;
import com.example.SpringSegurity.mapper.UserMapper;
import com.example.SpringSegurity.repository.UserRepository;

import com.example.SpringSegurity.security.JwtUtil;
import com.example.SpringSegurity.util.Estado;
import com.example.SpringSegurity.util.UserEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RefreshTokenService refreshTokenService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;


    @Override
    public LoginResponse login(LoginRequest request) {

        UserEntity user = userRepository
                .findByEmail(request.email())
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuario no encontrado"
                        )
                );

        validarEstadoCuenta(user);

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );

            reiniciarIntentosFallidos(user);

        } catch (BadCredentialsException e) {

            manejarIntentoFallido(user);

            throw e;
        }

        String accessToken =
                jwtUtil.generateToken(user.getEmail());

        RefreshTokenEntity refreshToken =
                refreshTokenService.createRefreshToken(user);

        return new LoginResponse(
                accessToken,
                refreshToken.getToken(),
                "Bearer"
        );
    }

    private void validarEstadoCuenta(UserEntity user) {

        if (user.getEstado() == Estado.INACTIVA) {

            throw new UnauthorizedException(
                    "Debes verificar tu email"
            );
        }

        if (user.getEstado() == Estado.BLOQUEADA) {

            if (user.getBlockedUntil() != null &&
                    user.getBlockedUntil()
                            .isAfter(LocalDateTime.now())) {

                throw new AccountBlockedException(
                        "La cuenta se encuentra bloqueada hasta "
                                + user.getBlockedUntil()
                );
            }

            desbloquearCuenta(user);
        }
    }

    private void desbloquearCuenta(UserEntity user) {

        user.setEstado(Estado.ACTIVA);

        user.setFailedLoginAttempts(0);

        user.setBlockedUntil(null);

        userRepository.save(user);
    }

    private void reiniciarIntentosFallidos(UserEntity user) {

        user.setFailedLoginAttempts(0);

        userRepository.save(user);
    }

    private void manejarIntentoFallido(UserEntity user) {

        int attempts =
                user.getFailedLoginAttempts() + 1;

        user.setFailedLoginAttempts(attempts);

        if (attempts >= 5) {

            user.setEstado(Estado.BLOQUEADA);

            user.setBlockedUntil(
                    LocalDateTime.now()
                            .plusMinutes(30)
            );
        }

        userRepository.save(user);
    }

    @Override
    public UserDTORes register(RegisterRequest request) {

        UserEntity user = UserEntity.builder()
                .nombre(request.nombre())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .userEnum(UserEnum.USER)
                .estado(Estado.INACTIVA)
                .failedLoginAttempts(0)
                .build();

        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        VerificationTokenEntity verificationToken =
                VerificationTokenEntity.builder()
                        .token(token)
                        .expirationDate(
                                LocalDateTime.now()
                                        .plusHours(24)
                        )
                        .user(user)
                        .build();

        verificationTokenRepository.save(
                verificationToken);

        emailService.sendVerificationEmail(
                user.getEmail(),
                token
        );

        return userMapper.toDTO(user);
    }

    @Override
    @Transactional
    public void verifyAccount(String token) {

        VerificationTokenEntity verificationToken =
                verificationTokenRepository
                        .findByToken(token)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Token inválido"
                                )
                        );

        if (verificationToken
                .getExpirationDate()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException("Token expirado");
        }

        UserEntity user = verificationToken.getUser();

        user.setEstado(Estado.ACTIVA);

        userRepository.save(user);

        verificationTokenRepository.delete(verificationToken);
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {

        UserEntity user =
                userRepository.findByEmail(request.email()).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        passwordResetTokenRepository.deleteByUser(user);

        String token = UUID.randomUUID().toString();

        PasswordResetTokenEntity resetToken = PasswordResetTokenEntity.builder()
                        .token(token)
                        .expirationDate(LocalDateTime.now().plusHours(1))
                        .user(user)
                        .build();

        passwordResetTokenRepository.save(resetToken);

        emailService.sendPasswordResetEmail(
                user.getEmail(),
                token
        );
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {

        PasswordResetTokenEntity resetToken =
                passwordResetTokenRepository
                        .findByToken(request.token())
                        .orElseThrow(() ->
                                new InvalidPasswordResetTokenException(
                                        "Token inválido"
                                )
                        );
        if (resetToken.getExpirationDate()
                .isBefore(LocalDateTime.now())) {

            throw new ExpiredPasswordResetTokenException(
                    "El token de recuperación ha expirado"
            );
        }

        UserEntity user = resetToken.getUser();

        user.setPassword(passwordEncoder.encode(request.newPassword()));

        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);
    }
}