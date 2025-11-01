package com.example.demo.Controller;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
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
		return "redirect:/department/index";
	}

	@GetMapping("/department/index")
	private String departmenlist(Model model) {
		List<Department> departments = departmentService.findAllDepartments();
		model.addAttribute("departments", departments);
		return "/department/index";
	}
	
	@PostMapping("/department/index")
	public String search(@RequestParam String keyword, Model model) {
		model.addAttribute("departments", departmentService.findByNameJpContainingOrNameEnContaining(keyword, keyword));
		model.addAttribute("keyword", keyword);
		return "/department/index";
	}
	
	@GetMapping("/department/edit")
	public String editDepartment(@RequestParam Long id, Model model, @ModelAttribute("departmentForm") DepartmentForm form) {
		model.addAttribute("department", departmentService.getEditDepartment(id));
	return "department/edit";
	}
	
	@PostMapping("/department/edit")
	public String updateDepartment(@ModelAttribute @Validated DepartmentForm departmentForm,
			BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "edit";
		}
		
		departmentService.updateDepartment(departmentForm);
		return "redirect:/department/index";
	}

	@GetMapping("/department/delete/{id}")
	public String deleteDepartment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
	    try {
	        departmentService.deleteDepartment(id);
	        redirectAttributes.addFlashAttribute("successMessage", "削除しました。");
	    } catch (EmptyResultDataAccessException e) {
	        redirectAttributes.addFlashAttribute("errorMessage", "指定された部署IDが見つかりませんでした。");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("errorMessage", "削除に失敗しました。");
	    }
	    return "redirect:/department/index";
	}
	
}
