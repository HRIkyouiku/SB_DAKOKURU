package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // 部署名に検索ワードを含むデータを取得
    List<Department> findByNameJpContaining(String keyword);

}
