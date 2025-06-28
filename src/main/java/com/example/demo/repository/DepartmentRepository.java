package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Department;

public interface DepartmentRepository {
    List<Department> userList(Long userId);

}
