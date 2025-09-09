package com.example.demo.validation.validator;

import java.lang.reflect.Field;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.example.demo.validation.annotation.AllOrNothingGroup;

public class AllOrNothingGroupValidator implements ConstraintValidator<AllOrNothingGroup, Object> {

    private String[] fields;
    private String message;

    @Override
    public void initialize(AllOrNothingGroup annotation) {
        this.fields = annotation.fields();
        this.message = annotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;

        boolean hasAnyInput = false;
        try {
            // 1つでも入力があるかをチェック
            for (String fieldName : fields) {
                Field field = value.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object fieldValue = field.get(value);
                if (fieldValue != null && !fieldValue.toString().trim().isEmpty()) {
                    hasAnyInput = true;
                    break; // 1つでも入力があれば終了
                }
            }

            // 全て空ならOK
            if (!hasAnyInput) return true;

            // 一つでも入力があれば未入力の欄にエラーメッセージ
            boolean hasViolation = false;
            context.disableDefaultConstraintViolation();
            for (String fieldName : fields) {
                Field field = value.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object fieldValue = field.get(value);
                if (fieldValue == null || fieldValue.toString().trim().isEmpty()) {
                    context.buildConstraintViolationWithTemplate(message)
                           .addPropertyNode(fieldName)
                           .addConstraintViolation();
                    hasViolation = true;
                }
            }
            return !hasViolation;

        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }
}