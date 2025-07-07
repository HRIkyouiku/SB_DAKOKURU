package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{
    
    private final DepartmentRepository departmentRepository;
    
    public List<Department> departmentList() {
        List<Department> list = departmentRepository.findAll();
        return list;
    }
}
