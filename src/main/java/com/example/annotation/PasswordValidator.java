package com.example.annotation;

import com.example.validations.PasswordStrengthValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = PasswordStrengthValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValidator {
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String message() default "Please choose a strong password!";

}
