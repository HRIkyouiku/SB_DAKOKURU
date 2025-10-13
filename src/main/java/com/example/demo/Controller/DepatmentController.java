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
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class DepatmentController {
    
    private final DepartmentService departmentService;
    
    @GetMapping("/department/index")
    private String index(Model model,@RequestParam(name = "searchWord", required = false) String searchWord) {
        if(searchWord != null) {
            List<Department> department = departmentService.departmentfindJplist(searchWord,searchWord);
            model.addAttribute("department", department);
            System.out.println(department);
        } else {
            List<Department> department = departmentService.departmentlist();
            model.addAttribute("department", department);
        }
        return "department/index";
    }
    
    @GetMapping("/department/create")
    private String create(Model model) {
        if (!model.containsAttribute("department")) {
            model.addAttribute("department", new Department());
        }
        return "department/create";
    }
    
    @PostMapping("/department/store")
    public String store(@Validated @ModelAttribute("department") Department form,
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
            ra.addFlashAttribute("org.springframework.validation.BindingResult.Department", result);
            ra.addFlashAttribute("department", form);
            return "redirect:/department/create";
        }

        Department department = new Department();
        department.setNameJp(form.getNameJp());
        department.setNameEn(form.getNameEn());
        departmentService.save(department);

        return "redirect:/department/index";
    }
    
    @GetMapping("/department/edit/{Id}")
    private String edit(Model model,  @PathVariable("Id") Long Id) {

        if (!model.containsAttribute("department")) {
            model.addAttribute("department", new Department());
        }
        
        Department department = departmentService.findById(Id).orElse(new Department());
        model.addAttribute("department", department);
        
        return "department/edit";
    }
     
    @PostMapping("/department/update/{Id}")
    public String update(@Validated @ModelAttribute("department") Department form,
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
            ra.addFlashAttribute("org.springframework.validation.BindingResult.department", result);
            ra.addFlashAttribute("department", form);

            String redirectUrl = UriComponentsBuilder
                    .fromPath("/department/edit/{id}")
                    .buildAndExpand(form.getId())
                    .toUriString();
            return "redirect:" + redirectUrl;
        }

        System.out.println(form.getId());
        Department department = departmentService.findById(form.getId()).orElse(new Department());
        department.setNameJp(form.getNameJp());
        department.setNameEn(form.getNameEn());
        departmentService.save(department);

        ra.addFlashAttribute("successMessage", "更新しました。");

        return "redirect:/department/edit/" + form.getId();
    }
    
    @PostMapping("/department/delete/{Id}")
    public String delete(@ModelAttribute("department") Department form) {
        departmentService.deleteById(form.getId());
        return "redirect:/department/index";
    }

}
