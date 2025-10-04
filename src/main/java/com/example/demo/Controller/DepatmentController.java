package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Department;
import com.example.demo.form.DepartmentForm;
import com.example.demo.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DepatmentController {

	private final DepartmentService departmentService;

	@GetMapping("/department/create")
	private String showCreationForm(@ModelAttribute("departmentForm") DepartmentForm form) {
		return "/department/create";
	}

	@PostMapping("/department/create")
	public String createDepartment(@Validated @ModelAttribute("departmentForm") DepartmentForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "/department/create";
		}

		Department createDepartment = new Department();
		createDepartment.setNameJp(form.getNameJp());
		createDepartment.setNameEn(form.getNameEn());
		departmentService.createDepartment(createDepartment);
		return "redirect:/login";
	}

	@GetMapping("/department/index")
	private String departmenlist(Model model) {
		List<Department> departments = departmentService.findAllDepartments();
		model.addAttribute("departments", departments);
		return "/department/index";
	}
	
	@GetMapping("/department/search")
	public String search(@RequestParam Long id, Model model) {
		model.addAttribute("departments", departmentService.getDepartmentById(id).orElse(null));
		return "/department/index";
	}
	
	@GetMapping("/department/edit")
	public String editDepartment(@RequestParam Long id, Model model) {
	Optional<Department> departmentOpt = departmentService.getEditDepartment(id);
	model.addAttribute("departmentOpt", departmentOpt.get());
	return "/department/edit";
	}

}
