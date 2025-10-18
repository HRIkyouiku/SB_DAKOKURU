package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;

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
import com.example.demo.form.DepartmentSeachForm;
import com.example.demo.form.ValidationGroups.DepartmentCreateGroup;
import com.example.demo.form.ValidationGroups.DepartmentUpdateGroup;
import com.example.demo.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    //部署一覧表示-------------------------
    @GetMapping("/department/index")
    private String DepartmentList(Model model, @Validated @ModelAttribute("departmentSeachForm") DepartmentSeachForm form){
        try {
            model.addAttribute("departments", departmentService.departmentList());
        } catch(NullPointerException e) {
            model.addAttribute("error", "データの取得に失敗しました。");
        }
         return "/department/index";
    }
    // キーワード検索-------------------------
    @GetMapping("/search")
    public String searchUsers(@RequestParam String keyword, Model model, @Validated @ModelAttribute("departmentSeachForm") DepartmentSeachForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "/department/index";
        }
        List<Department> users = departmentService.searchUsersByKeyword(keyword);
        model.addAttribute("users", users);
        return "/department/index"; // 同じテンプレートで結果を表示
    }
    //新規登録画面表示-------------------------
    @GetMapping("/department/create")
    public String createDepartment(Model model) {
        if (!model.containsAttribute("departmentForm")) {
            model.addAttribute("departmentForm", new DepartmentForm());
        }
        return "/department/create";
    }
    //新規登録処理-------------------------
    @PostMapping("/save")
    public String saveDepartment(@Validated(DepartmentCreateGroup.class) @ModelAttribute("departmentForm") DepartmentForm form,
            BindingResult result,
            RedirectAttributes ra) {
        Department departmentNameJp = departmentService.findByNameJp(form.getNameJp());
        if(departmentNameJp != null) {
            result.rejectValue("nameJp","duplicate.department","部署名は既に存在しています。");
        }
        Department departmentNameEn = departmentService.findByNameEn(form.getNameEn());
        if(departmentNameEn != null) {
            result.rejectValue("nameEn","duplicate.department","部署名は既に存在しています。");
        }
        if (result.hasErrors()) {
            ra.addFlashAttribute("org.springframework.validation.BindingResult.departmentForm", result);
            ra.addFlashAttribute("departmentForm", form);
            return "redirect:/department/create";
        }
        Department createDepartment= new Department();
        createDepartment.setNameJp(form.getNameJp());
        createDepartment.setNameEn(form.getNameEn());
        return "redirect:/department/index";
    }
    //編集フォーム表示
    @GetMapping("department/edit/{id}")
    public String editDepartment(@PathVariable Long id, Model model) {
        if (!model.containsAttribute("departmentForm")) {
            model.addAttribute("departmentForm", new DepartmentForm());
        }
        try {
            Optional<Department> department = departmentService.findById(id);
            model.addAttribute("department", department.get());
        } catch(NullPointerException e) {
            model.addAttribute("error", "データの取得に失敗しました。");
        }
        return "department/edit";
    }
    //削除
    @PostMapping("/department/delete")
    public String deleteDepartment(@RequestParam("id") Long id) {
        departmentService.deleteById(id);
        return "redirect:/department/index";
    }
    
    //編集機能
    @PostMapping("/department/update")
    public String updateDepartment(@Validated(DepartmentUpdateGroup.class) @ModelAttribute("departmentForm") DepartmentForm form,
            BindingResult result,
            RedirectAttributes ra) {
        Department departmentNameJp = departmentService.findByNameJp(form.getNameJp());
        if(departmentNameJp != null) {
            result.rejectValue("nameJp","duplicate.department","部署名は既に存在しています。");
        }
        Department departmentNameEn = departmentService.findByNameEn(form.getNameEn());
        if(departmentNameEn != null) {
            result.rejectValue("nameEn","duplicate.department","部署名は既に存在しています。");
        }
        if (result.hasErrors()) {
            ra.addFlashAttribute("org.springframework.validation.BindingResult.departmentForm", result);
            ra.addFlashAttribute("departmentForm", form);
            return "redirect:/department/edit/" + form.getId();
        }
        try {
            Optional<Department> department = departmentService.findById(form.getId());
            Department oldDepartment = department.get();
            String newNameJp = form.getNameJp();
            if(newNameJp != null && !newNameJp.isEmpty()) {
                oldDepartment.setNameJp(newNameJp);
            }
            String newNameEn = form.getNameEn();
            if(newNameEn != null && !newNameEn.isEmpty()) {
                oldDepartment.setNameEn(newNameEn);
            }
            departmentService.save(oldDepartment);
            ra.addFlashAttribute("message", "更新しました。");
        } catch(NullPointerException e) {
            ra.addFlashAttribute("error", "更新に失敗しました。");
            return "redirect:/department/edit/" + form.getId();
        }
        return "redirect:/department/index";
    }
}

















