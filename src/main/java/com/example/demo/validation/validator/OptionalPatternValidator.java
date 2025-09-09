package com.example.demo.validation.validator;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.example.demo.validation.annotation.OptionalPattern;

public class OptionalPatternValidator implements ConstraintValidator<OptionalPattern, String> {

    private Pattern pattern;

    @Override
    public void initialize(OptionalPattern annotation) {
        pattern = Pattern.compile(annotation.regexp());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true; // 空なら無視
        }
        return pattern.matcher(value).matches();
    }
}