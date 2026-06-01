package com.example.SpringSegurity.validation.annotation;

import com.example.SpringSegurity.validation.validator.ValidAmountValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidAmountValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAmount {

    String message() default
            "Monto inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}