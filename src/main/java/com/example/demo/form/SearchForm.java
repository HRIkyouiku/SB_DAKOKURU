package com.example.demo.form;

import jakarta.validation.constraints.Size;

import lombok.Data;

@Data

//検索フォーム
public class SearchForm implements ValidationGroups {
    //検索ワード
	@Size(max = 255, message = "キーワードは255文字以内で入力してください。")
    private String searchName;
    
    //部署ID
    private Long departmentId;
}
