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
    public void createDepartment(String nameJp, String nameEn) {
        Department department = new Department();
        department.setNameJp(nameJp);
        department.setNameEn(nameEn);
        departmentRepository.save(department);
    }

    @Override
    public List<Department> departmentList() {
        return departmentRepository.findAllByOrderByNameJpDesc();
    }

    @Override
    public Department findDepartmentById(Long id) {
        return departmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("部署が見つかりませんでした"));
    }

    @Override
    public void updateDepartment(Long id, String nameJp, String nameEn) {
        Department department = findDepartmentById(id);
        department.setNameJp(nameJp);
        department.setNameEn(nameEn);
        departmentRepository.save(department);
    }

    @Override
    public List<Department> departmentList(String searchName) {
        return departmentRepository.findByNameJpContainingIgnoreCaseOrNameEnContainingIgnoreCaseOrderByNameJpDesc(searchName, searchName);
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public boolean existsByNameJpExcludingId(String nameJp, Long id) {
        return departmentRepository.existsByNameJpAndIdNot(nameJp, id);
    }

    @Override
    public boolean existsByNameEnExcludingId(String nameEn, Long id) {
        return departmentRepository.existsByNameEnAndIdNot(nameEn, id);
    }

    @Override
    public boolean existsByNameJp(String nameJp) {
        return departmentRepository.existsByNameJp(nameJp);
    }

    @Override
    public boolean existsByNameEn(String nameEn) {
        return departmentRepository.existsByNameEn(nameEn);
    }
    
    @Override
    public long countDepartment(String searchName) {
        return departmentRepository.countByNameJpContainingIgnoreCaseOrNameEnContainingIgnoreCase(searchName, searchName);
    }
}
