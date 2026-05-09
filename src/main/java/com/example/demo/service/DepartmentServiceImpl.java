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
	public void registerDepartment(Department registerDepartment) {
		departmentRepository.save(registerDepartment);
	}
	
	@Override
	public boolean existsByNameJp(String nameJp) {
	    return departmentRepository.existsByNameJp(nameJp);
	}
	
	@Transactional
	public boolean existsByNameEn(String nameEn) {
		return departmentRepository.existsByNameEn(nameEn);
	}
	
	@Override
	public List<Department> findAllDepartments() {
		return departmentRepository.findAll();
	}
	
	@Transactional
	public Optional<Department> getDepartmentById(Long id) {
		return departmentRepository.findById(id);
	}
	
	@Override
	public List<Department> findByNameJpContainingOrNameEnContaining(String keyword, String keyworden) {
		return departmentRepository.findByNameJpContainingOrNameEnContaining(keyword, keyworden);
	}

}
