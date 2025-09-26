package com.example.demo.Controller;

import java.time.LocalDateTime;

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
import com.example.demo.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DepartmentController {
	private final DepartmentService departmentService;
	
    @GetMapping("/department/create")
    public String create(Model model) {
        if (!model.containsAttribute("departmentForm")) {
        	model.addAttribute("departmentForm", new DepartmentForm());
        }
        return "department/create";  
    }
    @PostMapping("/department/create")
    public String createDepartment(
    		@Validated @ModelAttribute("departmentForm") DepartmentForm form,
    		BindingResult result,
    		RedirectAttributes ra) {
    	
    	if(result.hasErrors()) {
    		ra.addFlashAttribute("org.springframework.validation.BindingResult.departmentForm",result);
    		ra.addFlashAttribute("departmentForm",form);
    		return "redirect:/department/create";
    	}
    	
    	Department department = new Department();
        department.setNameJp(form.getNameJp());
        department.setNameEn(form.getNameEn());
        LocalDateTime now= LocalDateTime.now();
        department.setCreatedAt(now);
        department.setUpdatedAt(now);

        // データベースに保存
        departmentService.save(department);

        // リダイレクト時にメッセージを追加
        ra.addFlashAttribute("successMessage", "部署が登録されました。");

        return "redirect:/department/list";
    }
    @GetMapping("/department/list")
    public String showDepartment(Model model) {
        model.addAttribute("departments", departmentService.findAll());
        return "department/list";
    }
}
