package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Department;
import com.example.demo.form.DepartmentForm;
import com.example.demo.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DepatmentController {

	private final DepartmentService departmentService;

	@GetMapping("/department/create")
	private String createDepartmentForm(@ModelAttribute("departmentForm") DepartmentForm form) {
	return "create";
	        }

	@PostMapping("/department/create")
	public String createDepartment(@Validated @ModelAttribute("departmentForm") DepartmentForm form,
	    BindingResult result) {
	        if (result.hasErrors()) {
	            return "redirect:/create";
	        }

	    Department createDepartment = new Department();
	    createDepartment.setNameJp(form.getName_jp());
	    createDepartment.setNameEn(form.getName_en());
	    departmentService.createDepartment(createDepartment);

	    return "/department/index";

	    }

}
