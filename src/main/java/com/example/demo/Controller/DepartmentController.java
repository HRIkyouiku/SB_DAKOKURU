package com.example.demo.Controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;

@Controller
public class DepartmentController {
	private final DepartmentRepository departmentRepository;
	
    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
    @GetMapping("/department/create")
    public String showCreateForm(Model model) {
        model.addAttribute("department", new Department());
        return "department/create";  
    }
    @PostMapping("/department/create")
    public String createDepartment(Department department) {
    	LocalDateTime now = LocalDateTime.now();
    	
    	if (department.getCreatedAt() == null) {
            department.setCreatedAt(now);
        }
        if (department.getUpdatedAt() == null) {
            department.setUpdatedAt(now);
        }
        if (department.getDeletedAt() == null) {
            department.setDeletedAt(null);
        }
        departmentRepository.save(department);
        return "redirect:/department/list";
    }
    @GetMapping("/department/list")
    public String showDepartment(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
        return "department/list";
    }
}
