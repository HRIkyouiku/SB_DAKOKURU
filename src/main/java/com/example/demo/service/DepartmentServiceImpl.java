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
	public List<Department> findAll() {
		List<Department> list = departmentRepository.findAll();
        return list;
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
	public void save(Department department) {
		departmentRepository.save(department);
	}
	
	@Override
	public Optional<Department> findById(Long departmentId) {
		return departmentRepository.findById(departmentId);
	}
}
