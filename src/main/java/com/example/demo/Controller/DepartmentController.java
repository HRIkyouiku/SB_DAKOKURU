package com.example.demo.Controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;

@Controller
public class DepartmentController {

	private final DepartmentService departmentService;
	
	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	@GetMapping("/department/index")
	private String departmentList(
			@RequestParam(name = "search", required = false) String search,
			Model model) {
		List<Department> list;
		
		if (search != null && search.length() > 255) {
			model.addAttribute("errorMessage", "キーワードは255文字以内で入力してください。");
			list = departmentService.departmentList();
		} else if (search != null && !search.trim().isEmpty()) {
	        // 検索ワードが入力されている場合
	        list = departmentService.findByNameJpContainingOrNameEnContaining(search, search);
	    } else {
	        // 検索ワードが空の場合（全件取得）
	        list = departmentService.departmentList();
	    }
		model.addAttribute("departmentList", list);
		model.addAttribute("search", search); 
		return "department/department";
	}
	
	@GetMapping("/department/create")
	private String createDepartment(Model model) {
		model.addAttribute("department", new Department());
		return "department/create";
	}
	
	@PostMapping("/department/store")
	public String storeDepartment(
			@Valid @ModelAttribute Department department, 
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model) {
		boolean existsNameJp = departmentService.existsByNameJp(department.getNameJp());
		
		if (existsNameJp) 
		result.rejectValue("nameJp", "duplicate", "部署名は既に存在しています。");
		boolean existsNameEn = departmentService.existsByNameEn(department.getNameEn());
		
		if (existsNameEn) 
		result.rejectValue("nameEn", "duplicate", "部署名（英語）は既に存在しています。");
		
		if(result.hasErrors())
		return "department/create";
		
		try {
			departmentService.storeDepartment(department);
			redirectAttributes.addFlashAttribute("processMessage", "登録しました。");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "登録に失敗しました。");
		}
		
		return "redirect:/department/index";
	}
	
	@GetMapping("/department/edit/{id}")
	private String editDepartment(
			@PathVariable Long id,
			Model model) {
		
			Department original = departmentService.findById(id);
			
			Department formDepartment = new Department();
			formDepartment.setId(original.getId());
			
			model.addAttribute("originalDepartment", original);
			model.addAttribute("department", formDepartment);
		return "department/edit";
	}
	
	@PutMapping("/department/update/{id}")
	public String updateDepartment(
			@PathVariable("id") Long id,
			@Valid @ModelAttribute Department department,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model) {
			boolean existsNameJp = departmentService.existsByNameJpAndIdNot(department.getNameJp(), id);
			if (existsNameJp) 
			result.rejectValue("nameJp", "duplicate", "部署名は既に存在しています。");
			
			boolean existsNameEn = departmentService.existsByNameEnAndIdNot(department.getNameEn(), id);
			if (existsNameEn) 
			result.rejectValue("nameEn", "duplicate", "部署名は既に存在しています。");
			
			if (result.hasErrors()) {
				Department original = departmentService.findById(id);
				model.addAttribute("originalDepartment", original);
				return "department/edit";
			}
			
			try {
				departmentService.storeDepartment(department);
				redirectAttributes.addFlashAttribute("processMessage", "登録しました。");
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("errorMessage", "更新に失敗しました。");
			}
			return "redirect:/department/index";
	}
	
	@DeleteMapping("/department/delete/{id}")
	public String deleteDepartment(
			@ModelAttribute Department department,
			@PathVariable("id") Long id,
			RedirectAttributes redirectAttributes) {
			
			try {
				departmentService.deleteById(id);
				redirectAttributes.addFlashAttribute("processMessage", "削除しました。");
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("errorMessage", "削除に失敗しました。");
			}
			return "redirect:/department/index";
	}
}			
