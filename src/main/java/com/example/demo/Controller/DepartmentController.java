package com.example.demo.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Department;
import com.example.demo.form.DepartmentForm;
import com.example.demo.form.ValidationGroups.DepartmentGroup;
import com.example.demo.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DepartmentController {
	private final DepartmentService departmentService;

	@GetMapping("/department/create")
    public String showRegistrationForm(@ModelAttribute("DepartmentForm") DepartmentForm form) {
        return "/department/create";
    }

	@PostMapping("/department/store")
    public String registerDepartment(
    		@Validated(DepartmentGroup.class)
    		@ModelAttribute("DepartmentForm") DepartmentForm form,
    		BindingResult result) {
        	if (result.hasErrors()) {
        		return "/department/create";
        	}
        Department registerDepartment = new Department();
        registerDepartment.setNameJp(form.getName_jp());
        registerDepartment.setNameEn(form.getName_en());
        departmentService.create(registerDepartment);
        return "redirect:/department/create";
    }

    @GetMapping("/department/index")
    public String departmentList(
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {
            List<Department> list;
            if (keyword == null || keyword.isEmpty()) {
                list = departmentService.findAll();
            } else {
                list = departmentService.searchByNameJp(keyword);
            }
            model.addAttribute("departmentList", list);
            model.addAttribute("keyword", keyword);
            return "/department/index";
        }
    
    @GetMapping("/department/edit/{Id}")
    public String departmentEdit(
        @PathVariable("Id") Long departmentId,
        Model model) {
    	Department department = departmentService.findDepartmentById(departmentId);
        model.addAttribute("department", department);
        return "/department/edit";
    }

    @PostMapping("/department/update")
    public String departmentUpdate(@ModelAttribute Department department) {
        departmentService.update(department);
        return "redirect:department/edit";
    }
    
    @PostMapping("/department/delete")
    public String departmentDelete(@RequestParam("id") Long id) {
        departmentService.delete(id);
        return "redirect:/department/index";
    }
}
