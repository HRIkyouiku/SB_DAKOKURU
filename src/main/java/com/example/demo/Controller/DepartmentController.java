package com.example.demo.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
import com.example.demo.form.ValidationGroups.DepartmentCreateGroup;
import com.example.demo.form.ValidationGroups.DepartmentUpdateGroup;
import com.example.demo.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DepartmentController {
	
	private final DepartmentService departmentService;
	
	@GetMapping("/department/index")
	public String index(Model model) {
		List<Department> list = departmentService.findAll();
		model.addAttribute("departmentList", list);
		return "departments/index";
	}
	
	@GetMapping("/department/search")
	public String search(@RequestParam(value = "searchName", required = false) String searchName,
						 Model model) {
		
		// 検索フラグ
		model.addAttribute("isSearch", true);
		
		// 空入力の場合は0件を表示
		if(!StringUtils.hasText(searchName)) {
			model.addAttribute("departmentList", List.of());
			model.addAttribute("resultCount", 0);
			return "departments/index";
		}
		
		// 文字数バリデーションチェック・検索フラグ
		if(searchName.length() > 255) {
			model.addAttribute("errorMessage", "検索ワードは255文字以内で入力してください。");
			List<Department> list = departmentService.findAll();
			model.addAttribute("departmentList", list);
			model.addAttribute("isSearch", false);
			return "departments/index";
		}
		
		// 検索＆件数取得
		List<Department> list = departmentService.departmentList(searchName);
		model.addAttribute("departmentList", list);
		model.addAttribute("searchResultCount", list.size());
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
		
		// バリデーションチェック後に重複チェック
		if (!result.hasErrors()) {
			if(departmentService.existsByNameJp(form.getNameJp())) {
				result.rejectValue("nameJp", "duplicate", "部署名は既に存在しています。");
			}
		
			if(departmentService.existsByNameEn(form.getNameEn())) {
				result.rejectValue("nameEn", "duplicate", "部署名（英語）は既に存在しています。");
			}
		}
		
		// バリデーションチェック
		if(result.hasErrors()) {
			ra.addFlashAttribute("org.springframework.validation.BindingResult.departmentForm", result);
            ra.addFlashAttribute("departmentForm", form);
			return "departments/create";
		}
		
		try {
			Department department = new Department();
			department.setNameJp(form.getNameJp());
			department.setNameEn(form.getNameEn());
			departmentService.save(department);

			// リダイレクト時にメッセージを追加
			ra.addFlashAttribute("successMessage", "登録しました。");
			
		} catch (Exception e) {
			ra.addFlashAttribute("errorMessage", "更新に失敗しました。");
			return  "redirect:/department/index";
		}

        return "redirect:/department/index";
	}
	
	@GetMapping("/department/edit/{departmentId}")
	public String edit(Model model,
					   @PathVariable("departmentId") Long departmentId) {
		
		Department department = departmentService.findById(departmentId).orElse(new Department());
		model.addAttribute("department", department);
		
		if (!model.containsAttribute("departmentForm")) {
	        model.addAttribute("departmentForm", new DepartmentForm());
	    }
		
		model.addAttribute("departmentId", departmentId);

		return "departments/edit";
	}
	
	@PostMapping("/department/update/{departmentId}")
	public String update(Model model,
						 @PathVariable("departmentId") Long departmentId, 
						 @Validated(DepartmentUpdateGroup.class) @ModelAttribute("departmentForm") DepartmentForm form,
						 BindingResult result,
						 RedirectAttributes ra) {
		
		Department department = departmentService.findById(departmentId).orElse(null);
	    if (department == null) {
	        ra.addFlashAttribute("errorMessage", "データの取得に失敗しました。");
	        return "redirect:/department/index";
	    }
		
	    // バリデーションチェック後に重複チェック
	    if(!result.hasErrors()) {
	    	if(departmentService.existsByNameJp(form.getNameJp())) {
	    		result.rejectValue("nameJp", "duplicate", "部署名は既に存在しています。");
	    	}
		
	    	if(departmentService.existsByNameEn(form.getNameEn())) {
	    		result.rejectValue("nameEn", "duplicate", "部署名（英語）は既に存在しています。");
	    	}
	    }
	    
	    // バリデーションチェック
		if(result.hasErrors()) {
			ra.addFlashAttribute("org.springframework.validation.BindingResult.departmentForm", result);
            ra.addFlashAttribute("departmentForm", form);
            return "redirect:/department/edit/{departmentId}";
		}
		
		try {
			department.setNameJp(form.getNameJp());
			department.setNameEn(form.getNameEn());
			departmentService.save(department);
		
			// リダイレクト時にメッセージを追加
			ra.addFlashAttribute("successMessage", "更新しました。");
        
		} catch (Exception e) {
			ra.addFlashAttribute("errorMessage", "更新に失敗しました。");
			return  "redirect:/department/index";
		}
		
		return "redirect:/department/edit/{departmentId}";
	}
	
	@PostMapping("/department/delete/{departmentId}")
	public String delete(@PathVariable("departmentId") Long departmentId,
			             RedirectAttributes ra) {
		
		try {
			departmentService.deleteById(departmentId);
			ra.addFlashAttribute("successMessage", "削除しました。");
		} catch(Exception e) {
			ra.addFlashAttribute("errorMessage", "削除に失敗しました。");
		}
		
		return  "redirect:/department/index";
	}
}
