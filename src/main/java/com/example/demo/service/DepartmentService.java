package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Department;

public interface DepartmentService {

    // 部署一覧
	List<Department> getAllDepartments();
    // 部署検索メソッド
    List<Department> searchDepartments(String keyword);
    // IDで取得
    Department findById(Long id);
    // 新規登録
    void saveDepartment(Department department);
    // 編集の更新
    void updateDepartment(Department department);
    // 部署削除
    void deleteDepartment(Long id);
}
