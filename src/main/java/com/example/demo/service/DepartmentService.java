package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Department;

public interface DepartmentService {
	
    //部署データのリスト取得
    List<Department> departmentfindAll();
	
}
