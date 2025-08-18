package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Department;

public interface DepartmentService {
	public List<Department> findAllByOrderByCreatedAtDesc();
	public void save(Department department);

}
