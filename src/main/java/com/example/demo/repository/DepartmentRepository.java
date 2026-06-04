package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findAllByOrderByNameJpDesc();
    List<Department> findByNameJpContainingOrNameEnContainingOrderByNameJpDesc(String nameJp, String nameEn);
    boolean existsByNameJp(String nameJp);
    boolean existsByNameEn(String nameEn);
    boolean existsByNameJpAndIdNot(String nameJp, Long id);
    boolean existsByNameEnAndIdNot(String nameEn, Long id);
}
