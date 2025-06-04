package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Department;

public interface DepartmentService {
	
    //部署データのリスト取得
    List<Department> departmentfindAll();

    //部署新規登録機能
    //Departmentエンティティのインスタンス「createDepartment」(フォームに入力された部署名)を渡す
    void createDepartment(Department createDepartment);

}
