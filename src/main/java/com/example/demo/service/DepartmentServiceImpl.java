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

	@Override
	public void createDepartment(Department createDepartment) {
		departmentRepository.save(createDepartment);
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
	
	@Transactional
	public Department updateDepartment(DepartmentForm form) {
		Department entity = new Department();
		entity.setId(form.getId());
		entity.setNameJp(form.getNameJp());
		entity.setNameEn(form.getNameEn());
		return departmentRepository.save(entity);
	}
	
	@Transactional
	public void deleteDepartment(Long id) {
		departmentRepository.deleteById(id);
	}

}
