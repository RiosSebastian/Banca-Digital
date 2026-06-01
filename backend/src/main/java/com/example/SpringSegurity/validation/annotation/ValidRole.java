package com.example.SpringSegurity.validation.annotation;

import com.example.SpringSegurity.validation.validator.ValidRoleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidRoleValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRole {

    String message() default "Rol inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
