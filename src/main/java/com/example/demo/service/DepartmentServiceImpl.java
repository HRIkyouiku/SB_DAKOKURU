package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Department;
import com.example.demo.form.DepartmentForm;
import com.example.demo.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class DepartmentServiceImpl implements DepartmentService {

	private final DepartmentRepository departmentRepository;

	@Override
	public void createDepartment(Department createDepartment) {
		departmentRepository.save(createDepartment);
	}

	@Override
	public List<Department> findAllDepartments() {
		return departmentRepository.findAll();
	}
	
	@Transactional
	public Optional<Department> getDepartmentById(Long id) {
		return departmentRepository.findById(id);
	}

	@Transactional
	public DepartmentForm getEditDepartment(Long id) {
		
		Optional<Department> departmentOpt = departmentRepository.findById(id);
		Department entity = departmentOpt.get();
		
		DepartmentForm form = new DepartmentForm();
		form.setId(id);
		form.setNameJp(entity.getNameJp());
		form.setNameEn(entity.getNameEn());
		return form;
	}
	
	

}
