package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    
    @Override
    //部署データのリスト取得(departmentRepositoryのfindAllメソッド)
    public List <Department> departmentfindAll() {
    
        //すべてのレコードをnameJpの降順で取得
        return departmentRepository.findAll(Sort.by(Sort.Direction.DESC, "nameJp"));
    }

    @Override
    //部署新規登録機能(departmentRepositoryのsaveメソッド)
    public void createDepartment(Department createDepartment) {
       
        //レコード登録(saveメソッド)
        departmentRepository.save(createDepartment);
    }
    
    @Override
    //部署編集・削除画面
    public Optional<Department> editDepartmentById(Long departmentId) {
        
        //レコード検索
        return departmentRepository.findById(departmentId);
    }
    
    @Transactional
    //部署編集機能
    public void updateDepartment(Department updateDepartment) {
        
        //レコード更新(saveメソッド)
        departmentRepository.save(updateDepartment);
    }
    
    @Override
    //部署削除機能
    public void deleteDepartment(Long departmentId) {
        
        //レコード削除(deleteメソッド)
        departmentRepository.deleteById(departmentId);
    }
    
    
    //部署検索機能
    public List <Department> departmentfindList(String searchName, String searchName2) {
        
        //部署検索(nameJp,nameEnであいまい検索、nameJpの降順で取得)
        return departmentRepository.findByNameJpLikeOrNameEnLikeOrderByNameJpDesc(
            "%" + searchName  + "%", "%" + searchName2  + "%");        
    }
    
    //部署存在チェック
    public boolean isnamejpExists(String nameJp) {
        return departmentRepository.existsByNameJp(nameJp); 
    }
    
    public boolean isnameenExists(String nameEn) {
        return departmentRepository.existsByNameEn(nameEn); 
    }
    
}