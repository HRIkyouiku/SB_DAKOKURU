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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Department;
import com.example.demo.form.DepartmentForm;
import com.example.demo.form.SearchForm;
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
    public String index (@ModelAttribute("message") String message, Model model,
            @ModelAttribute("searchForm") SearchForm form,
            @ModelAttribute("error") String error) {

        model.addAttribute("SearchForm", new SearchForm());
        //部署データをリスト取得(departmentServiceのdepartmentfindAllメソッド)
        model.addAttribute("departmentList", departmentService.departmentfindAll());

        //完了メッセージ
        model.addAttribute("message", message);
        model.addAttribute("error",error);
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
            @Validated(DepartmentCreateGroup.class)
            @ModelAttribute("departmentForm") DepartmentForm form,BindingResult result, RedirectAttributes redirectAttributes) {
        //バリデーションエラーがあった場合
        //再度 create テンプレートを表示
        if (result.hasErrors()) {
            return "/department/create";
            }

        //バリデーションエラーがなかった場合
        //部署名存在チェック
        if(departmentService.isnamejpExists(form.getNameJp())) {
        model.addAttribute("existsmessage_jp", "部署名は既に存在しています。");
        return "/department/create";
        }
        //部署名（英語）存在チェック 
        else if(departmentService.isnameenExists(form.getNameEn())) {
        model.addAttribute("existsmessage_en", "部署名（英語）は既に存在しています。");
        return "/department/create";
        }
        //Departmentエンティティのインスタンス「createDepartment」を作成
        Department createDepartment= new Department();	
        //createDepartmentに、DepartmentFormから取得したnameJpとnameEnをセット
        createDepartment.setNameJp(form.getNameJp());
        createDepartment.setNameEn(form.getNameEn());

        //departmentServiceのcreateDepertmentメソッドに、
        //「createDepartment」インスタンス(フォームに入力された部署名)を渡す
        departmentService.createDepartment(createDepartment);
        
        //処理完了メッセージ
        redirectAttributes.addFlashAttribute("message", "登録が完了しました。");
        
        // リダイレクト先：/department/index
        return  "redirect:/department/index";
    }
    
    //部署名編集・削除画面表示
    @GetMapping("/depertment/edit/{departmentId}") 
    private String editDepartment(Model model,
            @PathVariable("departmentId") Long departmentId,
            @ModelAttribute("departmentForm") DepartmentForm form,
            @ModelAttribute("message") String message) {
        
        //department.idをdepartmentServiceに渡してレコード取得
        Optional<Department> department = departmentService.editDepartmentById(departmentId);
        model.addAttribute("department", department.get());

        //完了メッセージ
        model.addAttribute("message", message);
        
        // リダイレクト先：/department/index
        return "/department/edit";
    }
    
    //部署名更新機能
    @PostMapping("/department/update/{departmentId}")
    public String updateDepartment (Model model,
            @PathVariable("departmentId") Long departmentId,
            @Validated(DepartmentUpdateGroup.class)
            @ModelAttribute("departmentForm") DepartmentForm form,
            BindingResult result,RedirectAttributes redirectAttributes) {
        
        Optional<Department> department = departmentService.editDepartmentById(departmentId);
        model.addAttribute("department", department.get());
        
        //バリデーションエラーがあった場合
        if (result.hasErrors()) {
            //再度 edit テンプレートを表示
            return "/department/edit";
            }
        //バリデーションエラーがなかった場合
        //部署名存在チェック
        if(departmentService.isnamejpExists(form.getNameJp())) {
            model.addAttribute("existsmessage_jp", "部署名は既に存在しています。");
            return "/department/edit";
            }
        //部署名（英語）存在チェック 
        else if(departmentService.isnameenExists(form.getNameEn())) {
            model.addAttribute("existsmessage_en", "部署名（英語）は既に存在しています。");
            return "/department/edit";
            }
        
        //部署名が存在しない場合
        //Departmentエンティティのインスタンス「updateDepartment」を作成
        Department updateDepartment= new Department();	
        //updateDepartmentに、DepartmentFormから取得したIdとnameJpとnameEnをセット
        updateDepartment.setId(form.getId());
        updateDepartment.setNameJp(form.getNameJp());
        updateDepartment.setNameEn(form.getNameEn());
 
        //更新内容を渡す
        departmentService.updateDepartment(updateDepartment);
        
        //処理完了メッセージ
        redirectAttributes.addFlashAttribute("message", "更新が完了しました。");

        // リダイレクト先：/department/edit
        return  "redirect:/depertment/edit/{departmentId}";
    }
    
    //部署削除機能
    @PostMapping("/post/delete/{departmentId}")
    private String postDelete(Model model, @PathVariable("departmentId") Long departmentId,
            RedirectAttributes redirectAttributes,@ModelAttribute("searchForm") SearchForm form) {
        try {
        //postServiceのdeletePostメソッドに、postIdを渡す
        departmentService.deleteDepartment(departmentId);
        //削除完了メッセージ
        redirectAttributes.addFlashAttribute("message", "削除が完了しました。");
        }
        catch(NullPointerException e) {
            //例外処理
            model.addAttribute("error", "削除が失敗しました。");
            return  "/department/index";
        }

        // リダイレクト先：/department/index
        return  "redirect:/department/index";
    }
    
    //部署検索
    @PostMapping("/depertment/search")
    private String findDepartmentList( Model model,
            @Validated @ModelAttribute("searchForm") SearchForm form,
            BindingResult result) {
        //検索ワードバリデーション
        if (result.hasErrors()) {            
            //再度 searchテンプレートを表示
            return "/department/index";
        }
        
        //検索ワード
        String searchName = form.getSearchName();
        model.addAttribute("searchName", searchName);
        
        //部署名（英語）用変数
        String searchName2 = searchName;
        
        //部署データをリスト取得(departmentServiceのdepartmentfindListメソッド)
        model.addAttribute("departmentList",
            departmentService.departmentfindList(searchName, searchName2));
        
        //レコード全件数
        model.addAttribute("countall", departmentService.departmentCountall());
        
        //検索件数
        model.addAttribute("count", departmentService.departmentCount(searchName, searchName2));
        
        // リダイレクト先：/department/index
        return  "/department/index";
    }

}
