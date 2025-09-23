package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public DepartmentForm getEditDepartment(Long departmentId) {
		// データベースから情報を取得
		Optional<Department> departmentOpt = repository.findById(departmentId);
		Department entity = departmentOpt.get();

		// CompanyFormオブジェクトを作成してプロパティを設定
		DepartmentForm form = new DepartmentForm();
		form.setDepartmentId(departmentId);
		form.setNameJp(entity.getNameJp());
		form.setNameEn(entity.getNameEn());
		return form;
	}

}
