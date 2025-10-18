package com.example.demo.service;

import java.util.List;
import java.util.Optional;

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
    //新規部署登録機能----------------------
    @Override
    public void saveDepartment(Department departments) {
        departmentRepository.save(departments);
    }
    
    @Override
    public Optional<Department> findById(Long id) {
        return departmentRepository.findById(id);
    }
    
    @Override
    public void deleteById(Long id) {
        departmentRepository.deleteById(id);
    }
    
    @Override
    public List<Department> findAll(){
        return departmentRepository.findAll();
    }
    
    @Override
    public Department findByNameJp(String nameJp){
        return departmentRepository.findByNameJp(nameJp);
    }
    
    @Override
    public Department findByNameEn(String nameEn){
        return departmentRepository.findByNameEn(nameEn);
    }
    
    @Override
    public void save(Department departmens) {
        departmentRepository.save(departmens);
    }
}
