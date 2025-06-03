package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Department;

@Repository

//JpaRepositoryの継承 <Departmentエンティティ, 主キーLong型>
public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
