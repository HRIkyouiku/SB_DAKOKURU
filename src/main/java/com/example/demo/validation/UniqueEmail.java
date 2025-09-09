package com.example.demo.validation;

import jakarta.validation.Payload;

public @interface UniqueEmail {
	String message() default "{com.example.demo.validation.UniqueEmail.message}";
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
