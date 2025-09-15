package com.example.demo.form;

import lombok.Data;

@Data

//検索フォーム
public class SearchForm implements ValidationGroups {
    //検索ワード
    private String searchName;
    
    //部署ID
    private Long departmentId;
}
