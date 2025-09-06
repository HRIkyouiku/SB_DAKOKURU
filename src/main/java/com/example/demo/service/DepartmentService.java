package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Department;

public interface DepartmentService {

    //入力値のvalidationチェックメソッド
    public Integer checkStoreInput(Department department);
    //部署を追加・変更するメソッド
    public Integer storeDepartment(Department department);
    //部署全件取得メソッド
    public List<Department> departmentList();
    //部署検索メソッド
    public List<Department> departmentSearchList(String searchWord);
    //IDから部署取得
    public Department getDepartment(Long DepartmentId);
    //部署を削除するメソッド
    public Integer deleteDepartment(Long departmentId);

}
