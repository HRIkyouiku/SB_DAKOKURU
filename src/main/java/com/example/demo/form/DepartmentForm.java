package com.example.demo.form;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class DepartmentForm implements ValidationGroups {

	private Long id;

	@NotBlank(message = "部署名を入力してください。")
	@Length(min = 1, max = 255, message = "部署名は1文字以上、255文字以内で入力してください。")
    private String name_jp;

	@NotBlank(message = "部署名(英語)を入力してください。")
	@Length(min = 1, max = 255, message = "部署名(英語)は1文字以上、255文字以内で入力してください。")
	@Pattern(regexp = "[a-zA-Z0-9]*", message = "部署名（英語）は半角英数字で入力してください。")
    private String name_en;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    private LocalDateTime deleted_at;
}
