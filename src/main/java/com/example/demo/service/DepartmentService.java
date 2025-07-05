package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;

public interface DepartmentService {


    public List<Department> departmentList() {
        List<Department> list = DepartmentRepository.findAll();
        return list;
    }
}
