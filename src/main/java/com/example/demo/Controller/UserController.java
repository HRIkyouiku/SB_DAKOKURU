package com.example.demo.Controller;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.entity.Name;
import com.example.demo.entity.User;
import com.example.demo.form.UserForm;
import com.example.demo.service.NameService;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final NameService nameService;

    @GetMapping("/user/create")
    public String create(Model model) {
        if (!model.containsAttribute("userForm")) {
            model.addAttribute("userForm", new UserForm());
        }
        return "users/create";
    }

    @PostMapping("/user/store")
    public String store(@Validated @ModelAttribute("userForm") UserForm form,
            BindingResult result, RedirectAttributes ra) {

        User existingEmail = userService.findByEmail(form.getEmail());
        if (existingEmail != null) {
            result.rejectValue("email", "duplicate.email", "メールアドレスは既に存在しています。");
        }

        User existingEmployeeNo = userService.findByEmployeeNo(form.getEmployeeNo());
        if (existingEmployeeNo != null) {
            result.rejectValue("employeeNo", "duplicate.employeeNo", "社員番号が既に存在しています。");
        }

        if (result.hasErrors()) {
            ra.addFlashAttribute("org.springframework.validation.BindingResult.userForm", result);
            ra.addFlashAttribute("userForm", form);
            return "redirect:/user/create";
        }

        User user = new User();
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setEmployeeNo(form.getEmployeeNo());
        user.setJoiningDate(form.getJoiningDate());
        userService.save(user);

        Name name = new Name();
        name.setFnJp(form.getFnJp());
        name.setFnJpHira(form.getFnJpHira());
        name.setFnJpKata(form.getFnJpKata());
        name.setFnEn(form.getFnEn());
        name.setLnJp(form.getLnJp());
        name.setLnJpHira(form.getLnJpHira());
        name.setLnJpKata(form.getLnJpKata());
        name.setLnEn(form.getLnEn());
        name.setOlnJp(form.getOlnJp());
        name.setOlnJpHira(form.getOlnJpHira());
        name.setOlnJpKata(form.getOlnJpKata());
        name.setOlnEn(form.getOlnEn());
        name.setMnJp(form.getMnJp());
        name.setMnJpHira(form.getMnJpHira());
        name.setMnJpKata(form.getMnJpKata());
        name.setMnEn(form.getMnEn());
        name.setEnglishNotation(Optional.ofNullable(form.getEnglishNotation()).orElse(false));
        name.setUser(user);
        nameService.save(name);

        ra.addFlashAttribute("successMessage", "ユーザーの登録に成功しました。");

        return "redirect:/user/index";
    }

    @GetMapping("/user/show/{userId}")
    public String show(Model model, @PathVariable("userId") Long userId) {

        User user = userService.findById(userId).orElse(new User());
        model.addAttribute("user", user);
        return "users/show";
    }

    @GetMapping("/user/edit/{userId}")
    public String edit(Model model, @PathVariable("userId") Long userId) {

        if (!model.containsAttribute("userForm")) {
            model.addAttribute("userForm", new UserForm());
        }

        User user = userService.findById(userId).orElse(new User());
        model.addAttribute("user", user);
        return "users/edit";
    }

    @PostMapping("/user/update")
    public String update(@Validated @ModelAttribute("userForm") UserForm form,
            BindingResult result, RedirectAttributes ra) {

        User existingEmail = userService.findByEmail(form.getEmail());
        if (existingEmail != null && !existingEmail.getId().equals(form.getId())) {
            result.rejectValue("email", "duplicate.email", "メールアドレスは既に存在しています。");
        }

        User existingEmployeeNo = userService.findByEmployeeNo(form.getEmployeeNo());
        if (existingEmployeeNo != null && !existingEmployeeNo.getId().equals(form.getId())) {
            result.rejectValue("employeeNo", "duplicate.employeeNo", "社員番号が既に存在しています。");
        }

        if (result.hasErrors()) {
            ra.addFlashAttribute("org.springframework.validation.BindingResult.userForm", result);
            ra.addFlashAttribute("userForm", form);

            String redirectUrl = UriComponentsBuilder
                    .fromPath("/user/edit/{userId}")
                    .buildAndExpand(form.getId())
                    .toUriString();
            return "redirect:" + redirectUrl;
        }

        System.out.println(form.getId());
        User user = userService.findById(form.getId()).orElse(new User());
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setEmployeeNo(form.getEmployeeNo());
        user.setJoiningDate(form.getJoiningDate());
        userService.save(user);

        Name name = nameService.findByUserId(form.getId());
        name.setFnJp(form.getFnJp());
        name.setFnJpHira(form.getFnJpHira());
        name.setFnJpKata(form.getFnJpKata());
        name.setFnEn(form.getFnEn());
        name.setLnJp(form.getLnJp());
        name.setLnJpHira(form.getLnJpHira());
        name.setLnJpKata(form.getLnJpKata());
        name.setLnEn(form.getLnEn());
        name.setOlnJp(form.getOlnJp());
        name.setOlnJpHira(form.getOlnJpHira());
        name.setOlnJpKata(form.getOlnJpKata());
        name.setOlnEn(form.getOlnEn());
        name.setMnJp(form.getMnJp());
        name.setMnJpHira(form.getMnJpHira());
        name.setMnJpKata(form.getMnJpKata());
        name.setMnEn(form.getMnEn());
        name.setEnglishNotation(Optional.ofNullable(form.getEnglishNotation()).orElse(false));
        nameService.save(name);

        ra.addFlashAttribute("successMessage", "ユーザーの更新に成功しました。");

        return "redirect:/user/index";
    }

    @PostMapping("/user/destroy")
    public String destroy(@ModelAttribute("userForm") UserForm form) {
        userService.deleteById(form.getId());
        return "redirect:/user/index";
    }
}
