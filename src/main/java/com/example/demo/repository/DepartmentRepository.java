package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

	Department findByNameJp(String NameJp);
	Department findByNameEn(String NameEn);
	boolean existsByNameJp(String value);
	Optional<Department> findById(Long id);
	void deleteById(Long id);
	List<Department> findByNameJpContainingIgnoreCaseOrderByNameJpDesc(String keyword);
	List<Department> findAllByOrderByNameJpDesc();
	List<Department> findByNameJpContainingIgnoreCaseOrNameEnContainingIgnoreCaseOrderByNameJpDesc(String keyword, String keyword2);
}
