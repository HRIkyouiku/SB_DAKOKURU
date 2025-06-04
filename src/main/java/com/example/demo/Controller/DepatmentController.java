package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Department;
import com.example.demo.form.DepartmentForm;
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
        
    //部署登録画面 
    @GetMapping ("/department/create")
    public String create () {
        //部署登録画面の表示
        return  "/department/create";
        
    }
    
    //部署新規登録機能
    @PostMapping("/department/store")
    public String store (Model model,
            @ModelAttribute("departmentForm") DepartmentForm form) {

        //Departmentエンティティのインスタンス「createDepartment」を作成
        Department createDepartment= new Department();	
        //createDepartmentに、DepartmentFormから取得したnameJpとnameEnをセット
        createDepartment.setNameJp(form.getNameJp());
        createDepartment.setNameEn(form.getNameEn());

        //departmentServiceのcreateDepertmentメソッドに、
        //「createDepartment」インスタンス(フォームに入力された部署名)を渡す
        departmentService.createDepartment(createDepartment);
        
        // リダイレクト先：department/index
        return  "redirect:/department/index";
    }
}
