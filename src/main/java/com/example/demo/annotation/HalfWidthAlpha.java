package com.example.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.demo.Validator.HalfWidthAlphaValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HalfWidthAlphaValidator.class)
public @interface HalfWidthAlpha {
	String message() default "半角英数字で入力してください。";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
