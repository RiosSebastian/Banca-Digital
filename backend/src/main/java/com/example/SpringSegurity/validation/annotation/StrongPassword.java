package com.example.SpringSegurity.validation.annotation;

import com.example.SpringSegurity.validation.validator.StrongPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {

    String message() default
            "La contraseña debe contener mayúsculas, minúsculas, números y caracteres especiales";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}