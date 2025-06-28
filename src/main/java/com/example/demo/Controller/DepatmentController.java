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

import com.example.demo.entity.Department;
import com.example.demo.form.DepartmentForm;
import com.example.demo.form.ValidationGroups.DepartmentCreateGroup;
import com.example.demo.form.ValidationGroups.DepartmentSearchGroup;
import com.example.demo.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DepatmentController {
    private final DepartmentService departmentService;

    //部署一覧画面を表示--------------------------------------------------------
    @GetMapping("/department/index")
    public String listDisplay(@ModelAttribute("departmentForm") DepartmentForm form, Model model) {
        List<Department> departmentList = departmentService.getDepartmentList();
        
        if (departmentList != null && !departmentList.isEmpty()) {
        model.addAttribute("departmentList", departmentList);
        return "department/index";
        }
        model.addAttribute("failMsg", "データの取得に失敗しました。");
        return "department/index";
    }


	//部署検索結果画面を表示--------------------------------------------------------
	@PostMapping("/department/index/search")
	public String departmentSearch(@Validated(DepartmentSearchGroup.class) @ModelAttribute("departmentForm") DepartmentForm form,
        BindingResult result,
        @RequestParam("keyword") String keyword, Model model) {
        //バリデーションエラーがあるか
        if (result.hasErrors()) {
            return "department/index";
        }
        
        //検索ワードが空白かどうか判定
        if (keyword == null || keyword.trim().isEmpty()) {
            return "redirect:/department/index";
        }
        
        //検索結果
        List<Department> departmentSearchResult = departmentService.getDepartmentSearch(keyword);
        if (departmentSearchResult == null || departmentSearchResult.isEmpty()) {
            model.addAttribute("searchCount", "0");
        } else {
        model.addAttribute("departmentList", departmentSearchResult);
        //検索結果件数
            Long departmentSearchCount = departmentService.getDepartmentSearchCount(keyword);
            model.addAttribute("searchCount", departmentSearchCount);
        }

        return "department/index";
    }


    //部署新規追加画面を表示--------------------------------------------------------
    @GetMapping("/department/create")
    public String createDepartmentDisplay(@ModelAttribute("departmentForm") DepartmentForm form) {
        return "department/create";
    }


    //部署新規追加機能-----------------------------------------------------------------
    @PostMapping("/department/store")
    public String addDepartment(@Validated(DepartmentCreateGroup.class) @ModelAttribute("departmentForm") DepartmentForm form,
        BindingResult result, RedirectAttributes redirectAttributes, Model model,
        @RequestParam("name_jp") String name_jp,
        @RequestParam("name_en") String name_en) {
        if (result.hasErrors()) {
            return "department/create";
            }

        // 重複チェック
        boolean hasError = false;
        
        if (departmentService.existsByNameJp(name_jp)) {
        model.addAttribute("nameJpError", "部署名は既に存在しています。");
            hasError = true;
        }

        if (departmentService.existsByNameEn(name_en)) {
        model.addAttribute("nameEnError", "部署名(英語)は既に存在しています。");
            hasError = true;
        }

        if (hasError) {
            return "department/create";
        }
        
        //追加
        try {
            departmentService.addDepartment(name_jp, name_en);
            redirectAttributes.addFlashAttribute("addSuccessMsg", "登録しました。");
            return "redirect:/department/index";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("failMsg", "登録に失敗しました。");
            return "redirect:/department/index";
        }
        
    }
    
    //部署編集・削除画面を表示--------------------------------------------------------
    @GetMapping("/department/edit/{departmentsId}")
    public String editDisplay(@ModelAttribute("departmentForm") DepartmentForm form, 
        @PathVariable("departmentsId") Long departmentsId, Model model) {
        Department departmentName = departmentService.getDepartmentName(departmentsId);
        if (departmentName != null) {
            model.addAttribute("departmentName", departmentName);
            model.addAttribute("departmentId", departmentsId);
            return "department/edit";
            }
            model.addAttribute("failMsg", "データの取得に失敗しました。");
            return "department/edit";
        }
    
    
    //変更機能--------------------------------------------------------
    @PostMapping("/department/update/{departmentsId}")
    public String updateDepartment(@Validated(DepartmentCreateGroup.class) @ModelAttribute("departmentForm") DepartmentForm form, 
        BindingResult result, RedirectAttributes redirectAttributes, Model model,
        @PathVariable("departmentsId") Long departmentsId, 
        @RequestParam("name_jp") String name_jp, 
        @RequestParam("name_en") String name_en) {
        if (result.hasErrors()) {
            Department departmentName = departmentService.getDepartmentName(departmentsId);
            model.addAttribute("departmentName", departmentName);
            model.addAttribute("departmentId", departmentsId);
            return "department/edit";
            }

        // 重複チェック
        boolean hasError = false;
        
        if (departmentService.existsByNameJp(name_jp)) {
        model.addAttribute("nameJpError", "部署名は既に存在しています。");
            hasError = true;
        }

        if (departmentService.existsByNameEn(name_en)) {
            model.addAttribute("nameEnError", "部署名(英語)は既に存在しています。");
            hasError = true;
        }

        if (hasError) {
            Department departmentName = departmentService.getDepartmentName(departmentsId);
            model.addAttribute("departmentName", departmentName);
            model.addAttribute("departmentId", departmentsId);
            return "department/edit";
        }

        //変更
        try {
            departmentService.updateDepartment(departmentsId, name_jp, name_en);
             redirectAttributes.addFlashAttribute("updateSuccessMsg", "更新しました。");
             return "redirect:/department/edit/{departmentsId}";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("failMsg", "更新に失敗しました。");
            return "redirect:/department/index";
        }
       
    }
    
    
    //削除機能--------------------------------------------------------
    @PostMapping("/department/delete/{departmentsId}")
    public String deleteDepartment(@ModelAttribute("departmentForm") DepartmentForm form, 
        RedirectAttributes redirectAttributes,
        @PathVariable("departmentsId") Long departmentsId) {

        //削除
        try {
            departmentService.deleteDepartment(departmentsId);
            redirectAttributes.addFlashAttribute("deleteSuccessMsg", "削除しました。");
            return "redirect:/department/index";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("failMsg", "削除に失敗しました。");
            return "redirect:/department/index";
        }

    }
  }
