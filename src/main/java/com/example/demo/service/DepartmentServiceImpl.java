package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    
    @Override
    //部署データのリスト取得(departmentRepositoryのfindAllメソッド)
    public List <Department> departmentfindAll() {
    
        return departmentRepository.findAll();
        
    }

    @Override
    //部署新規登録機能(departmentRepositoryのsaveメソッド)
    public void createDepartment(Department createDepartment) {
        departmentRepository.save(createDepartment);
        
    }
    
    @Override
    //部署編集・削除画面
    public Optional<Department> editDepartmentById(Long departmentId) {
    	return departmentRepository.findById(departmentId);
    }
    
    @Transactional
    //部署編集機能
    public void updateDepartment(Department updateDepartment) {
    	departmentRepository.save(updateDepartment);
    }
    
    @Override
    //部署削除機能
    public void deleteDepartment(Long departmentId) {
    	departmentRepository.deleteById(departmentId);
    }
    
    
    //部署検索機能
    public List <Department> departmentfindList(String searchName) {
    
    return departmentRepository.findByNameJpLike("%" + searchName  + "%");   
}

}
