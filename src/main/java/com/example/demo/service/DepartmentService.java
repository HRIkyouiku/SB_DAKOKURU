package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Department;


public interface DepartmentService {
	List<Department> findAll();
	
	void save(Department department);
	
	Optional<Department> findById(Long departmentId);
	
}