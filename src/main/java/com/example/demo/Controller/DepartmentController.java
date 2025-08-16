package com.example.demo.Controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Department;

public class DepartmentController {
	@GetMapping("/department/index")
	public String departmentIndex(){
		List<Department> list = departmentService.departmentList(departmentId);
		model.addAttribute("department",department);
	    return "department/index";
	}

}
