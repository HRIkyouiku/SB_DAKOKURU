package com.example.demo.service;

import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;

public class DepartmentServiceImpl implements DepartmentService {

	private final DepartmentRepository departmentRepository;

	@Override
    public List<Department> findAllByUserIdOrderByCreatedAtDesc(Long userId) {

    	List<Department> result = ((DepartmentServiceImpl) departmentRepository).findAllByUserIdOrderByCreatedAtDesc(userId);

        return result;
    }

	@Override
	public void save(Department department) {
		departmentRepository.save(department);
	}

}
