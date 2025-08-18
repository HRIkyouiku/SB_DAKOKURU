package com.example.demo.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Department;
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

}
