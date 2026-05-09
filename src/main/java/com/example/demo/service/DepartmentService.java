package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Department;

public interface DepartmentService {
	
	void registerDepartment(Department registerDepartment);
	boolean existsByNameJp(String nameJp);
	boolean existsByNameEn(String nameEn);
	List<Department> findAllDepartments();
	Optional<Department> getDepartmentById(Long id);
	List<Department> findByNameJpContainingOrNameEnContaining(String keyword, String keyworden);
}
