package com.example.SpringSegurity.validation.validator;

import com.example.SpringSegurity.util.UserEnum;
import com.example.SpringSegurity.validation.annotation.ValidRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class ValidRoleValidator
        implements ConstraintValidator<ValidRole, String> {

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context
    ) {

        if (value == null) {
            return false;
        }

        return Arrays.stream(UserEnum.values())
                .anyMatch(role ->
                        role.name().equals(value));
    }
}
