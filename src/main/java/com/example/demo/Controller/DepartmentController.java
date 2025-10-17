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
import com.example.demo.form.ValidationGroups.DepartmentCreateGroup;
import com.example.demo.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    //部署一覧表示-------------------------
    @GetMapping("/department/index")
    private String DepartmentList(Model model){
         model.addAttribute("departments", departmentService.departmentList());
         return "/department/index";
    }
    // キーワード検索-------------------------
    @GetMapping("/search")
    public String searchUsers(@RequestParam String keyword, Model model) {
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
    public String editDepartment(@PathVariable Long id, Model model, @ModelAttribute("departmentForm") DepartmentForm form) {
        Optional<Department> department = departmentService.findById(id);
        model.addAttribute("department", department.get());
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
    public String updateDepartment(@ModelAttribute("departmentForm") DepartmentForm form, Model model) {
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
        return "redirect:/department/index";
    }
}

















