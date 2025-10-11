package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Department;

public interface DepartmentService {

	List<Department> findAllDepartments();//部署一覧の表示
	
	Optional<Department> findDepartmentById(Long id);//編集画面への遷移
	
	Department createDepartment(Department department); //新規部署の作成
	
	Department updateDepartment(Long id, Department departmentDetails);//既存部署の編集
	
	void deleteDepartment (Long id);//部署の削除
	
	List<Department> searchDepartments(String keyword);//部署の検索

	Optional<Department> findById(Long id);

}
