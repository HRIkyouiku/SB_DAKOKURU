package com.example.demo.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Department;
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
        model.addAttribute("department", new Department());
        return "/department/create";
    }
    //新規登録処理-------------------------
    @PostMapping("/save")
    public String saveDepartment(@ModelAttribute Department departments) {
        departmentService.saveDepartment(departments);
        return "redirect:/department/index";
    }
}
