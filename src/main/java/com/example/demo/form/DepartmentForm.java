package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class DepartmentForm implements ValidationGroups{
	
    private Long id;

    @NotBlank(message = "作成する部署名を入力してください。")
    private String nameJp;

    @NotBlank(message = "部署名（英語）を入力してください。")
    private String nameEn;
    
    public boolean isOldNameValid() {
    return true;
    }
}