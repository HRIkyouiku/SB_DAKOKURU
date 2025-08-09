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
import com.example.demo.service.DepartmentService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class DepatmentController {
    
    private final DepartmentService departmentService;
    
    @GetMapping("/department/index")
    private String index(Model model) {
        //List<Department> departmentRegistrarion = departmentService.departmentlist();
        //model.addAttribute("departmentRegistrarion", departmentRegistrarion);
        //System.out.println(departmentRegistrarion);

        return "department/index";
    }
    
    @GetMapping("/department/create")
    private String create(Model model) {
        if (!model.containsAttribute("departmentForm")) {
            model.addAttribute("departmentForm", new DepartmentForm());
        }
        return "department/create";
    }
    
    @PostMapping("/department/store")
    public String store(@Validated @ModelAttribute("departmentForm") DepartmentForm form,
            BindingResult result, RedirectAttributes ra) {

        Department existingNameJp = departmentService.findByNameJp(form.getNameJp());
        if (existingNameJp != null) {
            result.rejectValue("nameJp", "duplicate.nameJp", "部署名は既に存在しています。");
        }

        Department existingNameEn = departmentService.findByNameEn(form.getNameEn());
        if (existingNameEn != null) {
            result.rejectValue("nameEn", "duplicate.nameEn", "部署名（英語）は既に存在しています。");
        }

        if (result.hasErrors()) {
            ra.addFlashAttribute("org.springframework.validation.BindingResult.departmentForm", result);
            ra.addFlashAttribute("departmentForm", form);
            return "redirect:/department/create";
        }

        Department department = new Department();
        department.setNameJp(form.getNameJp());
        department.setNameEn(form.getNameEn());
        departmentService.save(department);

        return "redirect:/department/index";
    }

}
