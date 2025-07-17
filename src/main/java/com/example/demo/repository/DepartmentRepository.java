package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
	
	List<Department> findAllByUserIdOrderByCreatedAtDesc(Long userId);	
	

}
