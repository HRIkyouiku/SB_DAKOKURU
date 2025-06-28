package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Department;

public interface DepartmentRepository {

    List<Department> getDepartmentList();

    List<Department> getDepartmentSearch(String keyword);

    Long getDepartmentSearchCount(String keyword);
    
    boolean existsByNameJp(String name_jp);
	
	boolean existsByNameEn(String name_en);

    void addDepartment(String name_jp, String name_en);
    
    Department getDepartmentName(Long departmentsId);
    
    void updateDepartment(Long departmentsId, String name_jp, String name_en);
    
    void deleteDepartment(Long departmentsId);

}
