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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Department;
import com.example.demo.form.DepartmentForm;
import com.example.demo.form.ValidationOrder;
import com.example.demo.service.DepartmentService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DepartmentController {
	
	private final DepartmentService departmentService;

	@GetMapping("/department/index")
	public String index(Model model) {
		List<Department> departments = departmentService.findAllByOrderByNameJpDesc();
		model.addAttribute("departments", departments);
		return "departments/index";
	}
	
	@GetMapping("/department/search")
	public String search(@RequestParam(required = false) String keyword, RedirectAttributes ra,Model model) {
		List<Department> departments = departmentService.findAllByOrderByNameJpDesc();
		try {
			if(keyword != null && !keyword.trim().isEmpty()) {
				departments = departmentService.searchByKeyword(keyword);
			} else {
				departments = departmentService.findAllByOrderByNameJpDesc();
			}
			model.addAttribute("departments", departments);
			model.addAttribute("keyword", keyword);
			} catch  (Exception e){
				ra.addFlashAttribute("errorMessage", "データの取得に失敗しました。");
				return "redirect:/department/index";
			}
		return "departments/index";
	}
	
	@GetMapping("/department/create")
	public String create(Model model) {
		model.addAttribute("departmentForm", new DepartmentForm());
		return "departments/create";
	}
	
	@PostMapping("/department/store")
	public String store(
			@Validated(ValidationOrder.class) @ModelAttribute("departmentForm") DepartmentForm form, 
			BindingResult result,
			RedirectAttributes ra,
			Model model) {
		
		Department nameJp = departmentService.findByNameJp(form.getNameJp());
			if(nameJp != null) {
				result.rejectValue("nameJp","duplicate.department","部署名は既に存在しています。");
			}
			
			Department nameEn = departmentService.findByNameEn(form.getNameEn());
			if(nameEn != null) {
				result.rejectValue("nameEn","duplicate.department","部署名（英語）は既に存在しています。");
			}
			
			System.out.println("errors: " + result.getAllErrors());

			if(result.hasErrors()) {
				model.addAttribute("org.springframework.validation.BindingResult.departmentForm", result);
				model.addAttribute("departmentForm", form);
				return "departments/create";
			}
		
			Department department = new Department();
			department.setNameJp(form.getNameJp());
			department.setNameEn(form.getNameEn());
			departmentService.save(department);
			ra.addFlashAttribute("successMessage", "登録しました。");
			ra.addFlashAttribute("ErrorMessage", "登録に失敗しました。");

		return "redirect:/department/index";
	}
	
	@GetMapping("/department/edit/{departmentId}")
	public String edit(@PathVariable("departmentId") Long id, Model model) {
		Department department = departmentService.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(""));
		
		DepartmentForm form = new DepartmentForm();
		form.setNameJp(department.getNameJp());
		form.setNameEn(department.getNameEn());
		model.addAttribute("departmentForm", form);
		model.addAttribute("department", department);
		
		return "departments/edit";
	}
	
	@PostMapping("/department/update/{departmentId}")
	public String update(
		@PathVariable("departmentId") Long id,
		@Validated (ValidationOrder.class) @ModelAttribute("departmentForm") DepartmentForm form,
		BindingResult result,
		RedirectAttributes ra,
		Model model) {
		
		Department existing = departmentService.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("データの取得に失敗しました。"));

		if(!form.getNameJp().equals(existing.getNameJp()) && departmentService.findByNameJp(form.getNameJp()) != null) {
			result.rejectValue("nameJp", "duplicate.department", "部署名はすでに存在しています。");
		}
		if(!form.getNameEn().equals(existing.getNameEn()) && departmentService.findByNameEn(form.getNameEn()) != null) {
			result.rejectValue("nameEn", "duplicate.department", "部署名（英語）はすでに存在しています。");
		}
		if(result.hasErrors()) {
			model.addAttribute("org.springframework.validation.BindingResult.departmentForm", result);
			model.addAttribute("departmentForm", form);
			model.addAttribute("department", existing);
			return "departments/edit";
		}
		
		existing.setNameJp(form.getNameJp());
		existing.setNameEn(form.getNameEn());
		departmentService.save(existing);
        ra.addFlashAttribute("successMessage", "更新しました。");
		
		
		return "redirect:/department/edit/{departmentId}";	
		}
	
	@PostMapping("/department/delete/{departmentId}")
	public String delete(@PathVariable("departmentId") Long id,
			RedirectAttributes ra) {
		departmentService.deleteById(id);
		
		ra.addFlashAttribute("successMessage", "削除しました。");
		ra.addFlashAttribute("ErrorMessage", "削除に失敗しました。");
		return "redirect:/department/index";
	}

}