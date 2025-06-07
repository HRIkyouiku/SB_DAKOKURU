package com.example.demo.Controller;

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

import com.example.demo.entity.Department;
import com.example.demo.form.DepartmentForm;
import com.example.demo.form.ValidationGroups.DepartmentCreateGroup;
import com.example.demo.form.ValidationGroups.DepartmentUpdateGroup;
import com.example.demo.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DepatmentController {
	
	private final DepartmentService departmentService;
	
    //部署一覧画面
    @GetMapping ("/department/index")
    public String index (Model model) {
        //部署データをリスト取得(departmentServiceのdepartmentfindAllメソッド)
        model.addAttribute("departmentList", departmentService.departmentfindAll());
        //部署一覧画面の表示
        return  "/department/index";
        
    }
        
    //部署登録画面表示
    @GetMapping ("/department/create")
    public String create (@ModelAttribute("departmentForm") DepartmentForm form) {
        //部署登録画面の表示
        return  "/department/create";
        
    }
    
    //部署新規登録機能
    @PostMapping("/department/store")
    public String store (Model model,
            @Validated(DepartmentCreateGroup.class) @ModelAttribute("departmentForm") DepartmentForm form,
            BindingResult result) {
        //バリデーションエラーがあった場合
        //再度 create テンプレートを表示
        if (result.hasErrors()) {
            return "/department/create";
            }
        //バリデーションエラーがなかった場合
        //Departmentエンティティのインスタンス「createDepartment」を作成
        Department createDepartment= new Department();	
        //createDepartmentに、DepartmentFormから取得したnameJpとnameEnをセット
        createDepartment.setNameJp(form.getNameJp());
        createDepartment.setNameEn(form.getNameEn());

        //departmentServiceのcreateDepertmentメソッドに、
        //「createDepartment」インスタンス(フォームに入力された部署名)を渡す
        departmentService.createDepartment(createDepartment);
        
        // リダイレクト先：/department/index
        return  "redirect:/department/index";
        }
    
    //部署名編集・削除画面表示
    @GetMapping("/depertment/edit/{departmentId}") 
    private String editDepartment(Model model,
            @PathVariable("departmentId") Long departmentId,
            @ModelAttribute("departmentForm") DepartmentForm form) {
        //department.idをdepartmentServiceに渡してレコード取得
        Optional<Department> department = departmentService.editDepartmentById(departmentId);
        model.addAttribute("department", department.get());

        return "/department/edit";
        }
    
    //部署名編集機能
    @PostMapping("/department/update/{departmentId}")
    public String updateDepartment (Model model,
            @PathVariable("departmentId") Long departmentId,
            @Validated(DepartmentUpdateGroup.class) @ModelAttribute("departmentForm") DepartmentForm form,
            BindingResult result) {
        //バリデーションエラーがあった場合
        if (result.hasErrors()) {
        	Optional<Department> department = departmentService.editDepartmentById(departmentId);
            model.addAttribute("department", department.get());
            //再度 edit テンプレートを表示
            return "/department/edit";
            }

        //Departmentエンティティのインスタンス「updateDepartment」を作成
        Department updateDepartment= new Department();	
        //updateDepartmentに、DepartmentFormから取得したIdとnameJpとnameEnをセット
        updateDepartment.setId(form.getId());
        updateDepartment.setNameJp(form.getNameJp());
        updateDepartment.setNameEn(form.getNameEn());
 
        //更新内容を渡す
        departmentService.updateDepartment(updateDepartment);
        // リダイレクト先：/department/index
        return  "redirect:/department/index";
        }
    
    //部署削除機能
    @PostMapping("/post/delete/{departmentId}")
    private String postDelete(
    	//@PathVariable: URLパスの中のプレースホルダー{departmentId}の値を取得
        @PathVariable("departmentId") Long departmentId) {
    	//postServiceのdeletePostメソッドに、postIdを渡す
        departmentService.deleteDepartment(departmentId);
        // リダイレクト先：/department/index
        return  "redirect:/department/index";
    }
    
    //部署検索
    @PostMapping("/depertment/search")
    private String searchDepartmentList( Model model,
            @RequestParam("searchName") String searchName) {

        //部署名（英語）用変数
        String searchName2 = searchName;
        //部署データをリスト取得(departmentServiceのdepartmentfindListメソッド)
        model.addAttribute("departmentList", departmentService.departmentfindList(searchName,searchName2));
            return  "/department/index";
    }
}
