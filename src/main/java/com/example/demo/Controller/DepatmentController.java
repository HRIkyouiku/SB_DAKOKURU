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
import com.example.demo.form.DepartmentForm;
import com.example.demo.form.DepartmentSearchForm;
import com.example.demo.service.DepartmentService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class DepatmentController {
    
    private final DepartmentService departmentService;
    
    @GetMapping("/department/index")
    private String index(Model model,@RequestParam(name = "searchWord", required = false) String searchWord,@Validated @ModelAttribute("departmentSearchForm") DepartmentSearchForm form,
            BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "department/index";
        }
        try {
            if(searchWord != null && !searchWord.isEmpty()) {
                List<Department> department = departmentService.departmentfindJplist(searchWord,searchWord);
                model.addAttribute("department", department);
                model.addAttribute("searchWord", searchWord);
            } else {
                List<Department> department = departmentService.departmentlist();
                model.addAttribute("department", department);
            }
        } catch(NullPointerException e) {
            ra.addFlashAttribute("error", "データの取得に失敗しました。");
            return "department/index";
        }
        
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
        
        try {
            Department department = new Department();
            department.setNameJp(form.getNameJp());
            department.setNameEn(form.getNameEn());
            departmentService.save(department);
        } catch(NullPointerException e) {
            ra.addFlashAttribute("error", "登録に失敗しました。");
            return "redirect:/department/create";
        }
        
        ra.addFlashAttribute("successMessage", "登録しました。");

        return "redirect:/department/index";
    }
    
    @GetMapping("/department/edit/{Id}")
    private String edit(Model model,  @PathVariable("Id") Long Id) {

        if (!model.containsAttribute("departmentForm")) {
            model.addAttribute("departmentForm", new DepartmentForm());
        }
        
        Department department = departmentService.findById(Id).orElse(new Department());
        model.addAttribute("department", department);
        
        return "department/edit";
    }
     
    @PostMapping("/department/update/{Id}")
    public String update(@Validated @ModelAttribute("departmentForm") DepartmentForm form,
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
    public String delete(@ModelAttribute("department") Department form,
            RedirectAttributes ra) {
        try {
            departmentService.deleteById(form.getId());
        } catch(NullPointerException e) {
            ra.addFlashAttribute("error", "削除に失敗しました。");
            return "redirect:/department/edit/" + form.getId();
        }
        ra.addFlashAttribute("successMessage", "削除しました。");
        return "redirect:/department/index";
    }

}
