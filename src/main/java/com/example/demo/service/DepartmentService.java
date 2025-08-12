package com.example.demo.service;

import com.example.demo.entity.Department;

public interface DepartmentService {
	boolean existsByNameJp(String nameJp);
	boolean existsByNameEn(String nameEn);
	public void save(Department department);
}
