package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model, String error) {

        if (error != null) {
            model.addAttribute("errorMessage", "社員番号とパスワードの組み合わせが正しくありません。");
        }

        return "login"; // login.html
    }
}
