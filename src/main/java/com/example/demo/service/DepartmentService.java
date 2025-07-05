package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repository.DepartmentRepository;

public interface DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Departments> getAllUsers() {
        return DepartmentRepository.findAll();
    }
}
