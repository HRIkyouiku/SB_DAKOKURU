package com.example.demo.form;

import jakarta.validation.constraints.Size;

import lombok.Data;

@Data

public class SearchDepartmentForm {

    @Size(max = 255, message = "キーワードは255文字以内で入力してください。")
    private String searchDepartment;

}
