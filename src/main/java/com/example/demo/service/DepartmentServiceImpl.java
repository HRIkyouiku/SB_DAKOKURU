package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<Department> departmentlist() {
        List<Department> list = departmentRepository.findAll();
        return list;
    }
    
    @Override
    public List<Department> departmentfindJplist(String searchWordJp,String searchWordEn) {
        List<Department> list = departmentRepository.findByNameJpContainingOrNameEnContaining(searchWordJp,searchWordEn) ;
        return list;
    }
    
    @Override
    public void save(Department department) {
    }
    
    @Override
    public Department findByNameJp(String nameJp) {
        return departmentRepository.findByNameJp(nameJp);
    }
    
    @Override
    public void deleteById(Long Id) {
        departmentRepository.deleteById(Id);
    }

    @Override
    public Department findByNameEn(String nameEn) {
        return departmentRepository.findByNameEn(nameEn);
    }
    
    @Override
    public Optional<Department> findById(Long Id) {
        return departmentRepository.findById(Id);
    }

}
