package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class DepartmentForm implements ValidationGroups {
	private Long id;
	@NotBlank(message = "部署名を入力してください。",groups = {DepartmentGroup.class, DepartmentUpdateGroup.class})
	@Size(max = 255, message = "部署名は1文字以上、255文字以内で入力してください。",groups = {DepartmentGroup.class, DepartmentUpdateGroup.class})
	private String name_jp;
	@NotBlank(message = "部署名（英語）を入力してください。",groups = {DepartmentGroup.class, DepartmentUpdateGroup.class})
	@Size(max = 255, message = "部署名（英語）は1文字以上、255文字以内で入力してください。",groups = {DepartmentGroup.class, DepartmentUpdateGroup.class})
    @Pattern(regexp = "^[A-Za-z0-9]+$",message = "部署名（英語）は半角英数字で入力してください。",groups = {DepartmentGroup.class, DepartmentUpdateGroup.class})
	private String name_en;
}