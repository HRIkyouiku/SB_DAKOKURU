package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	private final DepartmentRepository departmentRepository;
	
	public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
		this.departmentRepository =departmentRepository;
	}
	
	@Override
	public List<Department> departmentList(){
		return departmentRepository.findAll();
	}
	
	@Override
	public void storeDepartment(Department department) {
		departmentRepository.save(department);
	}
	
	@Override
	public Department findById(Long id) {
		return departmentRepository.findById(id).get();
	}
	
	@Override
	public Department updateDepartment(Long id,Department department) {
		Department update = departmentRepository.findById(id).get();
		update.setNameJp(department.getNameJp());
		update.setNameEn(department.getNameEn());
		return departmentRepository.save(update);
	}
	
	@Override 
	public void deleteById(Long id) {
		departmentRepository.deleteById(id);
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
	public boolean existsByNameJpAndIdNot(String nameJp, Long id) {
		return departmentRepository.existsByNameJp(nameJp);
	}
	
	@Override
	public boolean existsByNameEnAndIdNot(String nameEn, Long id) {
		return departmentRepository.existsByNameEn(nameEn);
	}
	
	@Override
	public List<Department> findByNameJpContainingOrNameEnContaining(String nameJp, String nameEn){
		return departmentRepository.findByNameJpContainingOrNameEnContaining(nameJp, nameEn);
	}	
}
