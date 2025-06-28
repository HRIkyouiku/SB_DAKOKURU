package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<Department> getDepartmentList() {
    	return departmentRepository.getDepartmentList();
    }

    @Override
    public List<Department> getDepartmentSearch(String keyword) {
    	return departmentRepository.getDepartmentSearch(keyword);
    }

    @Override
    public Long getDepartmentSearchCount(String keyword) {
    	return departmentRepository.getDepartmentSearchCount(keyword);
    }
    
    @Override //追加
    public boolean existsByNameJp(String name_jp) {
        return departmentRepository.existsByNameJp(name_jp);
    }

    @Override //追加
    public boolean existsByNameEn(String name_en) {
        return departmentRepository.existsByNameEn(name_en);
    }

    @Override
    public void addDepartment(String name_jp, String name_en) {
    	departmentRepository.addDepartment(name_jp, name_en);
    }
    
    @Override
    public Department getDepartmentName(Long departmentsId) {
        return departmentRepository.getDepartmentName(departmentsId);
    }
    
    @Override
    public void updateDepartment(Long departmentsId, String name_jp, String name_en) {
        departmentRepository.updateDepartment(departmentsId, name_jp, name_en);
    }
    
    @Override
    public void deleteDepartment(Long departmentsId) {
        departmentRepository.deleteDepartment(departmentsId);
    }

}
