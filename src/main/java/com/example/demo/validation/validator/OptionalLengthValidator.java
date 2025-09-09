package com.example.demo.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.example.demo.validation.annotation.OptionalLength;

public class OptionalLengthValidator implements ConstraintValidator<OptionalLength, String> {

    private int min;
    private int max;

    @Override
    public void initialize(OptionalLength annotation) {
        min = annotation.min();
        max = annotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) return true; // 空なら無視
        int length = value.trim().length();
        return length >= min && length <= max;
    }
}
