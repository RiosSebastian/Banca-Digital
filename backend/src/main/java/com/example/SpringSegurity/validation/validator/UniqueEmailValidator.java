package com.example.SpringSegurity.validation.validator;

import com.example.SpringSegurity.repository.UserRepository;
import com.example.SpringSegurity.validation.annotation.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator
        implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(
            String email,
            ConstraintValidatorContext context
    ) {

        return !userRepository.findByEmail(email)
                .isPresent();
    }
}
