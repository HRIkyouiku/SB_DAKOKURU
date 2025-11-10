package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Department;

public interface DepartmentService {

	List<Department> departmentList();
	public void storeDepartment(Department department);
	Department findById(Long id);
	Department updateDepartment(Long id, Department department);
	void deleteById(Long id);
	boolean existsByNameJp(String nameJp);
	boolean existsByNameEn(String nameEn);
	boolean existsByNameJpAndIdNot(String nameJp, Long id);
	boolean existsByNameEnAndIdNot(String nameEn, Long id);
	List<Department> findByNameJpContainingOrNameEnContaining(String nameJp, String nameEn);
}
