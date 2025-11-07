package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/login").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        // 追記
                        .loginProcessingUrl("/authenticate")
                        .usernameParameter("employee_no")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/timestamp/create", true)
                        // 追記
                        .failureHandler(customFailureHandler()) 
                        //
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .permitAll());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
        
    // 追記
    @Bean
    public AuthenticationFailureHandler customFailureHandler() {
        return (request, response, exception) -> {
            // 入力された社員番号を取得
            String employeeNo = request.getParameter("employee_no");

            // ログインページに「error」と「employee_no」を付けてリダイレクト
            response.sendRedirect("/login?error&employee_no=" + employeeNo);
        };
    }
}