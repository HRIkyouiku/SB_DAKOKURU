package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "employee_no", required = false) String employeeNo,
            Model model) {
        if (error != null) {
            model.addAttribute("error", "社員番号とパスワードの組み合わせが正しくありません。");
        }

        // 前回入力された社員番号を保持
        model.addAttribute("employee_no", employeeNo);

        return "login";
    }
    
    @PostMapping("/login")
    public String login(
            @RequestParam(required = false) String employee_no,
            @RequestParam(required = false) String password,
            Model model) {

        boolean hasError = false;

        // --- 社員番号未入力 または 数字以外 ---
        if (employee_no == null || employee_no.isBlank() || !employee_no.matches("\\d+")) {
            hasError = true;
        }

        // --- パスワード未入力 ---
        if (password == null || password.isBlank()) {
            hasError = true;
        }

        // 🔽 バリデーションエラーがあれば、認証処理せずエラーメッセージ表示
        if (hasError) {
            model.addAttribute("error", "社員番号とパスワードの組み合わせが正しくありません。");
            model.addAttribute("employee_no", employee_no);
            return "login";
        }

        // バリデーションOK → Spring Security に委譲
        return "forward:/authenticate";
    }
}