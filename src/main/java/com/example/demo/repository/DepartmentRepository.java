package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
	
	List<Department> findAllByOrderByCreatedAtDesc();	
	
	
	List<Department> findByNameJpLikeOrNameEnLikeOrderByNameJpDesc(String searchName,String searchNameEn);

}
