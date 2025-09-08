package com.example.demo.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import com.example.demo.validation.validator.AllOrNothingGroupValidator;

@Documented
@Constraint(validatedBy = {AllOrNothingGroupValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(AllOrNothingGroups.class)  // 複数指定を可能にする
public @interface AllOrNothingGroup {
    String message() default "各欄に一つでも入力があった場合は必須です。";
    String[] fields();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}