package com.example.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;

public class DepartmentController {
	@GetMapping("/department/index")
	public String departmentIndex(){
	    return "department/index";
	}

}
