package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
	
	boolean existsByNameJp(String nameJp);
	
	boolean existsByNameEn(String nameEn);
	
	List<Department> findAll();
	
	Department findDepartmentById(Long id);
	 
	 List<Department> findByNameJpContainingOrNameEnContaining(String keyword, String keyworden);

}
