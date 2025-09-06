package com.example.demo.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Department;
import com.example.demo.form.SearchForm;
import com.example.demo.service.DepartmentService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class DepartmentController {

	private final DepartmentService departmentService;
	@GetMapping("/department/index")
	public String departmentIndex(Model model){
		List<Department> list = departmentService. findAllByOrderByCreatedAtDesc();
		model.addAttribute("department",list);
	    return "department/index";
	}
	
	@PostMapping("/department/search")
	private String searchDepartmentList(
		@ModelAttribute("searchForm") SearchForm form,
		Model model) {
		String searchName=form.getSearchName();
		String searchNameEn=searchName;
		List<Department> list = departmentService.departmentList(searchName,searchNameEn);
		model.addAttribute("departmentList", list);
		return "department/index";	
		
	}

}
