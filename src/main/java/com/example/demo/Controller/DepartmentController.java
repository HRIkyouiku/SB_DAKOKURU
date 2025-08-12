package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Department;
import com.example.demo.form.DepartmentForm;
import com.example.demo.form.ValidationGroups.DepartmentCreateGroup;
import com.example.demo.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DepartmentController {
	
	private final DepartmentService departmentService;
	
	@GetMapping("/department/index")
	public String index() {
		return "departments/index";
	}
	
	@GetMapping("/department/create")
	private String create(@ModelAttribute("departmentForm") DepartmentForm form) {
		return "departments/create";
	}
	
	@PostMapping("/department/store")
	public String store(Model model, @Validated(DepartmentCreateGroup.class) @ModelAttribute("departmentForm") DepartmentForm form,
			BindingResult result,
			RedirectAttributes ra) {
		
		if(result.hasErrors()) {
			ra.addFlashAttribute("org.springframework.validation.BindingResult.departmentForm", result);
            ra.addFlashAttribute("departmentForm", form);
			return "departments/create";
		}
		
		if(departmentService.existsByNameJp(form.getNameJp())) {
			model.addAttribute("errorMessage", "部署名は既に存在しています。");
	        return "departments/create";
		}
		
		if(departmentService.existsByNameEn(form.getNameEn())) {
			model.addAttribute("errorMessage", "部署名（英語）は既に存在しています。");
	        return "departments/create";
		}
		
		Department department = new Department();
		department.setNameJp(form.getNameJp());
		department.setNameEn(form.getNameEn());

        // データベースに保存
		departmentService.save(department);

        // リダイレクト時にメッセージを追加
        ra.addFlashAttribute("successMessage", "登録しました。");

        return "redirect:/department/index";
	}
}
