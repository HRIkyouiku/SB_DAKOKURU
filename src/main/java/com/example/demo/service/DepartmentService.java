package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Department;

public interface DepartmentService {
	
	void createDepartment(Department createDepartment) ;
	
	List<Department> findAllDepartments();
	
	Optional<Department> getDepartmentById(Long id);
	
	Optional<Department> getEditDepartment(Long id);

}
