package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class DepartmentForm implements ValidationGroups {
	
	private Integer id;
    
    @NotBlank(message = "部署名を入力してください。")
    @Length(min = 1, max = 255, message = "部署名は1文字以上、255文字以内で入力してください。")
    private String departmentname_jp;
    
    @NotBlank(message = "部署（英語）を入力してください。")
    @Length(min = 1, max = 255, message = "部署名（英語）は1文字以上、255文字以内で入力してください。")
    private String departmentname_en;
    
}
