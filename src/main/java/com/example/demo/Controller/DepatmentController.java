package com.example.demo.Controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Department;
import com.example.demo.form.DepartmentForm;
import com.example.demo.form.SearchDepartmentForm;
import com.example.demo.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor

public class DepatmentController {

    private final DepartmentService departmentService;

    @GetMapping("/department/index")
    public String departmentList(Model model) {
            List<Department> list = departmentService.departmentList();
            model.addAttribute("departmentList",list);
            model.addAttribute("searchDepartmentForm", new SearchDepartmentForm());
        return "/department/index";
    }

    @GetMapping("/department/create")
    public String create(Model model) {
        model.addAttribute("departmentForm", new DepartmentForm());
        return "/department/create";
    }
    
    @PostMapping("/department/store")
    private String createDeprtment(
        @ModelAttribute("departmentForm") @Valid DepartmentForm departmentForm,BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/department/create"; 
    }
        if (departmentService.existsByNameJp(departmentForm.getNameJp())) {
            result.rejectValue("nameJp", "", "部署名は既に存在しています。");
    }
        if (departmentService.existsByNameEn(departmentForm.getNameEn())) {
            result.rejectValue("nameEn", "", "部署名（英語）は既に存在しています。");
    }
        if (result.hasErrors()) {
            return "/department/create";
    }
            departmentService.createDepartment(
            departmentForm.getNameJp(),
            departmentForm.getNameEn()
            );
        return "redirect:/department/index";
    }

    @GetMapping("/department/edit/{departmentId}")
    private String editDepartment(
        @PathVariable("departmentId") Long departmentId,
        Model model){
            Department department = departmentService.findDepartmentById(departmentId);
            DepartmentForm form = new DepartmentForm();
            model.addAttribute("department", department);
            model.addAttribute("departmentForm", form);
        return "/department/edit";
    }
    
    @PostMapping("/department/update/{departmentId}")
    private String updateDepartment(
        @PathVariable("departmentId") Long departmentId,
        @ModelAttribute("departmentForm") @Valid DepartmentForm form, BindingResult result, Model model
        ) {
        if (result.hasErrors()) {
            model.addAttribute("department", departmentService.findDepartmentById(departmentId));
            return "/department/edit"; 
    }
        if (departmentService.existsByNameJpExcludingId(form.getNameJp(), departmentId)) {
            result.rejectValue("nameJp", "", "部署名は既に存在しています。");
    }
        if (departmentService.existsByNameEnExcludingId(form.getNameEn(), departmentId)) {
            result.rejectValue("nameEn", "", "部署名（英語）は既に存在しています。");
    }
        if (result.hasErrors()) {
            model.addAttribute("department", departmentService.findDepartmentById(departmentId));
            return "/department/edit";
    }
        String updateNameJp = form.getNameJp();
        String updateNameEn = form.getNameEn();
        departmentService.updateDepartment(departmentId, updateNameJp, updateNameEn);
        return "redirect:/department/index";
    }

    @PostMapping("/department/searchresult")
    private String searchDepartmentList(
    @ModelAttribute("searchDepartmentForm") @Valid SearchDepartmentForm form, BindingResult result, Model model
    ) {
    if (result.hasErrors()) {
        return "/department/index";
    }
        String searchDepartment = form.getSearchDepartment();
        List<Department>list = departmentService.departmentList(searchDepartment);
        long count = departmentService.countDepartment(searchDepartment);
        model.addAttribute("departmentList", list);
        model.addAttribute("searchDepartment", searchDepartment);
        model.addAttribute("countDepartment", count);
        return "/department/index";
    }

    @PostMapping("/department/delete/{departmentId}")
    private String deleteDepartment(
    @PathVariable("departmentId") Long departmentId) {
        departmentService.deleteDepartment(departmentId);
        return "redirect:/department/index";
    }
}
