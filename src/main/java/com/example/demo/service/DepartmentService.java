package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Department;

public interface DepartmentService {

	Department findByNameJp(String NameJp);
	Department findByNameEn(String NameEn);
	public void save(Department department);
	List<Department> findAllByOrderByNameJpDesc();
	Optional<Department> findById(Long id);
	void deleteById(Long departmentId);
	List<Department> searchByKeyword(String keyword);
}
