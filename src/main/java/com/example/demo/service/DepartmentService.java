package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Department;


public interface DepartmentService {

    //部署一覧表示-------------------------
    List<Department> departmentList();
    //検索機能----------------------------
    List<Department> searchUsersByKeyword(String keyword);

}
