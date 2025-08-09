package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

  //  @Override
//    public List<Department> departmentlist(Long name_jp) {

 //       List<Department> list = DepartmentRepository.departmentlist(name_jp);

//        return list;
   // }
    
    @Override
    public void save(Department department) {
        departmentRepository.save(department);
    }
    
    @Override
    public Department findByNameJp(String nameJp) {
        return departmentRepository.findByNameJp(nameJp);
    }

    @Override
    public Department findByNameEn(String nameEn) {
        return departmentRepository.findByNameEn(nameEn);
    }

}
