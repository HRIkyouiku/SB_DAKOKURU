package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {

    //NameJpが存在するか確認するメソッド
    boolean existsByNameJp(String nameJp);
    //NameEnが存在するか確認するメソッド
    boolean existsByNameEn(String nameEN);
    //NameJpを検索するメソッド
    List<Department> findAllByNameJpLike(String seachWord);
    //NameEnを検索するメソッド
    List<Department> findAllByNameEnLike(String seachWord);

}
