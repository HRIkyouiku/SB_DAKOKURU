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
    public List<Department> findAllByOrderByCreatedAtDesc() {

    	List<Department> result = departmentRepository.findAllByOrderByCreatedAtDesc();

        return result;
    }

	@Override
	public void save(Department department) {
		departmentRepository.save(department);
	}
	
    @Override
    public List<Department> departmentList(String searchName,String searchNameEn) {
            List<Department> list =departmentRepository.findByNameJpLikeOrNameEnLikeOrderByNameJpDesc(
                    "%" + searchName  + "%", "%" + searchNameEn  + "%");
            
            return list;
            
    }

}
