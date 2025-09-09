package com.example.demo.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import com.example.demo.validation.validator.OptionalPatternValidator;

@Documented
@Constraint(validatedBy = OptionalPatternValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionalPattern {
    String message() default "入力内容が不正です";
    String regexp();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}