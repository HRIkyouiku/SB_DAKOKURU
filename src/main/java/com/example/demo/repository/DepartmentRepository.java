package com.example.demo.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>  {

	Department findByNameJp(String nameJp);

    Department findByNameEn(String nameEn);
    
    Optional<Department> findById(Long Id);
    
    public List<Department>findAll();
    
    public List<Department>findByNameJpContainingOrNameEnContaining(String searchWordJp,String searchWordEn);
    
    public void deleteById(Long Id);

}
