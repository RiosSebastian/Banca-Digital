package com.example.SpringSegurity.validation.validator;

import com.example.SpringSegurity.validation.annotation.ValidAmount;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidAmountValidator
        implements ConstraintValidator<ValidAmount, Double> {

    @Override
    public boolean isValid(
            Double amount,
            ConstraintValidatorContext context
    ) {

        if (amount == null) {
            return false;
        }

        return amount > 0;
    }
}
