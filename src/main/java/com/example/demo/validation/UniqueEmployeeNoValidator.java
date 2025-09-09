package com.example.demo.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmployeeNoValidator implements ConstraintValidator<UniqueEmployeeNo, Long> {

    private final UserRepository userRepository;

    @Autowired
    public UniqueEmployeeNoValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UniqueEmployeeNo constraintAnnotation) {
        // 初期化処理があればここに記述します（今回は不要）
    }

    @Override
    public boolean isValid(Long employeeNo, ConstraintValidatorContext context) {
        // 社員番号がnullの場合は、他のバリデーション（@NotNull）に任せる
        if (employeeNo == null) {
            return true;
        }

        // データベースから社員番号を検索
        return !userRepository.findByEmployeeNo(employeeNo).isPresent();
    }
}