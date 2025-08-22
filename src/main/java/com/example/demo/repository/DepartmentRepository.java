package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{
	List<Department> findAll();
	boolean existsByNameJp(String nameJp);
	boolean existsByNameEn(String nameEn);
	Optional<Department> findById(Long departmentId);
}
