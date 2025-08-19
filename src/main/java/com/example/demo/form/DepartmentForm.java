package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data

public class DepartmentForm {

    @NotBlank(message = "部署名を入力してください")
    @Size(min = 1, max = 255, message = "部署名は1文字以上、255文字以内で入力してください。")
    private String nameJp;

    @NotBlank(message = "部署名（英語）を入力してください")
    @Size(min = 1, max = 255, message = "部署名(英語)は1文字以上、255文字以内で入力してください。")
    @Pattern(regexp = "^[a-zA-Z0-9\\ss\\-_.(),/&]+$", message = "部署名（英語）は半角英数字で入力してください。	")
    private String nameEn;

}
