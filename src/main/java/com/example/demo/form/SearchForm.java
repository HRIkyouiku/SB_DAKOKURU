package com.example.demo.form;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

//部署検索フォーム
@Data
public class SearchForm implements ValidationGroups {
	
	@Length(max=255, message = "キーワードは255文字以内で入力してください。")
	private String searchName;

}
