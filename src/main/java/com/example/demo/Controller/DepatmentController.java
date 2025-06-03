package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DepatmentController {
	
	private final DepartmentService departmentService;
	
    //部署一覧画面
    @GetMapping ("/department/index")
    public String index (Model model )  {
        //部署データをリスト取得(departmentServiceのdepartmentfindAllメソッド)
        model.addAttribute("departmentList", departmentService.departmentfindAll());
        //部署一覧画面の表示
        return  "/department/index";
        
    }
    
    //部署登録画面 
    @GetMapping ("create")
    public String create()  {
        return  "/department/create";
    }
    
}
