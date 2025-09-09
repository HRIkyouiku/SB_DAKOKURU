package com.example.demo.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidatator implements ConstraintValidator<UniqueEmail,String> {
	private final UserRepository userRepository;
	
	@Autowired
    public UniqueEmailValidatator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		//emailがnullまたは空の場合、他のバリデーション（＠Notblunkや@Emailに任せる）
		if(email == null || email.isEmpty()) {
			return true;
		}
		//データベースからメールアドレスを検索
		return !userRepository.findByEmail(email).isPresent();
	}
}
