package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Department;

public interface DepartmentService {
	public void create(Department Department);
	
	List<Department> findAll();
    
	List<Department> searchByNameJp(String keyword);
	
	Department findDepartmentById(Long id);
	
	public void update(Department Department);
	
	public void delete(Long id);
}
