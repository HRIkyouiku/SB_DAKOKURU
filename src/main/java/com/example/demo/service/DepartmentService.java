package com.example.demo.service;

import com.example.demo.entity.Department;

public interface DepartmentService {
	public List<Department> findAllByUserIdOrderByCreatedAtDesc(Long userId);
	public void save(Department department);

}
