package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Department;

public interface DepartmentService {
	
	void createDepartment(Department createDepartment) ;
	
	List<Department> findAllDepartments();
	
	Department findDepartmentById(Long departmentId);

}
