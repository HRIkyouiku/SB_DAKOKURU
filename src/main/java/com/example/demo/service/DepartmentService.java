package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Department;

public interface DepartmentService {

    public List<Department> departmentlist();
    
    public List<Department> departmentfindlist(String searchWord);
    
    public Optional<Department> findById(Long Id);

    public void save(Department department);
    
    public void deleteById(Long Id);
    
    Department findByNameJp(String nameJp);

    Department findByNameEn(String nameEn);

}
