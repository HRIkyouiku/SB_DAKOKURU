package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

    List<Department> findByNameJpContainingIgnoreCaseOrNameEnContainingIgnoreCase(String nameJp, String nameEn);

    boolean existsByNameJpAndIdNot(String nameJp, Long id);

    boolean existsByNameEnAndIdNot(String nameEn, Long id);

    boolean existsByNameJp(String nameJp);

    boolean existsByNameEn(String nameEn);

}
