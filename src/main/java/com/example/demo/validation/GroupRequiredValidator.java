package com.example.demo.validation;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GroupRequiredValidator implements ConstraintValidator<GroupRequired, Object> {

    private String[] fields;

    @Override
    public void initialize(GroupRequired constraintAnnotation) {
        this.fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        boolean anyFieldHasValue = false;
        
        // 1. グループ内のいずれかのフィールドに値があるかチェック
        for (String field : fields) {
            Object fieldValue = beanWrapper.getPropertyValue(field);
            if (fieldValue instanceof String && !((String) fieldValue).isEmpty()) {
                anyFieldHasValue = true;
                break;
            }
        }

        if (anyFieldHasValue) {
            // 2. いずれかのフィールドに値があれば、全てのフィールドが必須であるかチェック
            for (String field : fields) {
                Object fieldValue = beanWrapper.getPropertyValue(field);
                if (fieldValue == null || (fieldValue instanceof String && ((String) fieldValue).isEmpty())) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                           .addPropertyNode(field)
                           .addConstraintViolation();
                    return false;
                }
            }
        }

        return true;
    }
}