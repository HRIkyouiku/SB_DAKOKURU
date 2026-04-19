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
    public void create(Department Department) {
    	departmentRepository.save(Department);
    }
    
    @Override
    public List<Department> findAll() {
        return departmentRepository.findAllByOrderByNameJpDesc();
    }

    @Override
    public List<Department> searchByNameJp(String keyword) {
        return departmentRepository.findByNameJpContainingOrderByNameJpDesc(keyword);
    }
    
    @Override
    public Department findDepartmentById(Long id) {
    	return departmentRepository.findById(id).orElse(null);
    }
    
    @Override
    public void update(Department Department) {
    	departmentRepository.save(Department);
    }
    
    @Override
    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }
}
