package com.example.demo.Validator;

import java.util.regex.Pattern;

import com.example.demo.annotation.HalfWidthAlpha;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HalfWidthAlphaValidator implements ConstraintValidator<HalfWidthAlpha, String> {

	private static final Pattern HALF_WIDTH_ALPHA_PATTERN = Pattern.compile( "^[a-zA-Z0-9]+$");
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value == null || HALF_WIDTH_ALPHA_PATTERN.matcher(value).matches();
	}
	

}
