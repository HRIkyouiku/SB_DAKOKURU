package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{
    //部署一覧表示-------------------------
    List<Department> findAll();
    //検索機能----------------------------
    List<Department> findByNameJpContaining(String keyword);
    
    Optional<Department> findById(Long id);
    
    Department findByNameJp(String nameJp);
    
    Department findByNameEn(String nameEn);
}

