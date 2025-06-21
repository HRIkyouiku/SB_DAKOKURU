package com.example.demo.security;


import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        // エラーメッセージをリクエストに追加
        request.setAttribute("error", "メールアドレスまたはパスワードが正しくありません。");

        // エラーメッセージをログイン画面に渡すクエリパラメータを追加してリダイレクト
        response.sendRedirect("/login?error=true");
    }
}