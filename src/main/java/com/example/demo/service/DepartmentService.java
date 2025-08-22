package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Department;

public interface DepartmentService {
	public List<Department> findAll();
	boolean existsByNameJp(String nameJp);
	boolean existsByNameEn(String nameEn);
	public void save(Department department);
	public Optional<Department> findById(Long departmentId);
}
