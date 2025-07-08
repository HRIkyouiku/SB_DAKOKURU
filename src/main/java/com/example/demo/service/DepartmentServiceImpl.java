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
    //部署一覧表示-------------------------
    public List<Department> departmentList() {
        List<Department> list = departmentRepository.findAll();
        return list;
    }

    //検索機能----------------------------
    @Override
    public List<Department> searchUsersByKeyword(String keyword) {
        return departmentRepository.findByNameJpContaining(keyword);
    }
}
