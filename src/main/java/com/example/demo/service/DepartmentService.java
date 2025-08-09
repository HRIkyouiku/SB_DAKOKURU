package com.example.demo.service;

import com.example.demo.entity.Department;

public interface DepartmentService {
	
	//public List<Department> departmentlist(Long name_jp);

    public void save(Department department);
    
    Department findByNameJp(String nameJp);

    Department findByNameEn(String nameEn);

}
