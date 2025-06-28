package com.example.demo.Controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Department;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DepatmentController {
    
    @GetMapping("/department/index")
    private String userList(Model model,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getId();

         List<Department> list =DepartmentService.userList(userId);
         model.addAttribute("userList", list);

         return "/department/index";
    }
}
