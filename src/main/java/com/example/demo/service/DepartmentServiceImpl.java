package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
    
    // 部署一覧を取得
    @Override
    public List<Department> getAllDepartments() {
      return departmentRepository.findAll();
    }
    
    // 部署検索
    @Override
    public List<Department> searchDepartments(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return departmentRepository.findAll();
        }
        return departmentRepository.findByNameJpContaining(keyword);
    }
    
    // 部署の取得
    @Override
    public Department findById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }
    
    // 部署の新規登録
    @Override
    public void saveDepartment(Department department) {
    	departmentRepository.save(department);
    }
    
    // 部署の更新
    @Override
    public void updateDepartment(Department department) {
        departmentRepository.save(department);
    }
    
    // 部署の削除
    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
