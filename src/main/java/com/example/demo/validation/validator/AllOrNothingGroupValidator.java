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
        try {
            boolean hasAnyInput = false;
            boolean hasEmpty = false;

            for (String fieldName : fields) {
                Field field = value.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object fieldValue = field.get(value);

                boolean isEmpty = (fieldValue == null || "".equals(fieldValue.toString().trim()));
                if (!isEmpty) {
                    hasAnyInput = true;
                } else {
                    hasEmpty = true;
                }
            }

            // 1つ以上入力があり、かつ未入力もある場合はエラー
            if (hasAnyInput && hasEmpty) {
                context.disableDefaultConstraintViolation();
                for (String fieldName : fields) {
                    Field field = value.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    Object fieldValue = field.get(value);

                    boolean isEmpty = (fieldValue == null || "".equals(fieldValue.toString().trim()));
                    if (isEmpty) {
                        context.buildConstraintViolationWithTemplate(message)
                               .addPropertyNode(fieldName)
                               .addConstraintViolation();
                    }
                }
                return false;
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}