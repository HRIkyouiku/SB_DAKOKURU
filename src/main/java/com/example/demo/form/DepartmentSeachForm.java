package com.example.demo.form;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class DepartmentSeachForm implements ValidationGroups {
    @Length(max = 255, message = "キーワードは255文字以内で入力してください。")
    private String keyword;
}
