package com.example.demo.form;

import lombok.Data;

//部署新規登録フォーム
@Data
public class DepartmentForm implements ValidationGroups {

    private String nameJp;
    private String nameEn;

}
