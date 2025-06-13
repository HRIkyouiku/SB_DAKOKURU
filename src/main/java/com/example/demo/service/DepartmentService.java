package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Department;

public interface DepartmentService {
	
    //部署データのリスト取得
    List<Department> departmentfindAll();

    //部署新規登録機能
    //Departmentエンティティのインスタンス「createDepartment」(フォームに入力された部署名)を渡す
    void createDepartment(Department createDepartment);

    //部署編集画面
    Optional<Department> editDepartmentById(Long departmentId);
    
    //部署更新機能
    void updateDepartment(Department updateDepartment);

    //部署存在チェック
    boolean isnamejpExists(String nameJp);
    boolean isnameenExists(String nameEn);

    //部署削除機能
    void deleteDepartment(Long departmentId);
    
    //部署検索機能
    List<Department> departmentfindList(String searchName, String searchName2);
    
    
    
}
