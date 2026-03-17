package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;

@Controller
public class DepartmentController {
    
    @Autowired
    private DepartmentService departmentService;
    
    // 部署一覧画面
    @GetMapping("/department/index")
    public String showDepartments(
            @RequestParam(required = false) String keyword,
            Model model) {
        
        // 部署一覧の取得（検索ワードがある場合は検索）
        List<Department> departments = departmentService.searchDepartments(keyword);
        // モデルに登録
        model.addAttribute("departments", departments);
        model.addAttribute("keyword", keyword);
        
        return "departments/index";
    }
    
    // 新規登録画面
    @GetMapping("/department/create")
    public String showCreateForm() {
        return "departments/create";
    }
    
    // 新規登録処理
    @PostMapping("/department/store")
    public String storeDepartment(Department department) {
    	departmentService.saveDepartment(department);
    	return "redirect:/department/index";
    }
    
    
    // 編集画面
    @GetMapping("/department/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        // 指定IDの部署を取得
        Department department = departmentService.findById(id);
        // モデルに登録
        model.addAttribute("department", department);
        // edit.html を返す
        return "departments/edit";
    }
    
    // 更新処理
    @PostMapping("/department/update")
    public String updateDepartment(@ModelAttribute Department department) {
        departmentService.updateDepartment(department);
        return "redirect:/department/index";
    }
    
    // 削除処理
    @PostMapping("/department/delete/{id}")
    public String deleteDepartment(@PathVariable("id") Long id) {
        departmentService.deleteDepartment(id);
        return "redirect:/department/index";
    }
}
