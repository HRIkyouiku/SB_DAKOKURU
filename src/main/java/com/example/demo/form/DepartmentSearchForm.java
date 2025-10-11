package com.example.demo.form;

import jakarta.validation.constraints.Size; // @Size を使うためにインポート
import lombok.Data; // Lombok を使う場合

@Data 
public class DepartmentSearchForm {

    @Size(max = 255, message = "キーワードは255文字以内で入力してください。") // 最大文字数とメッセージを設定
    private String keyword;
}