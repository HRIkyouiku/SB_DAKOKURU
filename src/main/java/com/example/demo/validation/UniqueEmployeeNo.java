package com.example.demo.validation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = { UniqueEmployeeNoValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmployeeNo {

    String message() default "{com.example.demo.validation.UniqueEmployeeNo.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}