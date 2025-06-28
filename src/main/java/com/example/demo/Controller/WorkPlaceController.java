package com.example.demo.Controller;

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

import com.example.demo.entity.WorkPlace;
import com.example.demo.form.WorkPlaceForm;
import com.example.demo.service.WorkPlaceService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WorkPlaceController {

    private final WorkPlaceService workPlaceService;

    @GetMapping("/workPlace/create")
    public String create(Model model) {
        if (!model.containsAttribute("workPlaceForm")) {
            model.addAttribute("workPlaceForm", new WorkPlaceForm());
        }
        return "worlkPlaces/create";
    }

    @PostMapping("/workPlace/store")
    public String store(@Validated @ModelAttribute("workPlaceForm") WorkPlaceForm form,
            BindingResult result, RedirectAttributes ra) {

        if (result.hasErrors()) {
            ra.addFlashAttribute("org.springframework.validation.BindingResult.workPlaceForm", result);
            ra.addFlashAttribute("workPlaceForm", form);
            return "redirect:/workPlace/create";
        }

        WorkPlace workPlace = new WorkPlace();
        workPlace.setName(form.getName());

        // データベースに保存
        workPlaceService.save(workPlace);

        // リダイレクト時にメッセージを追加
        ra.addFlashAttribute("successMessage", "新しい勤務場所が登録されました。");

        return "redirect:/workPlace/create";
    }

    @GetMapping("/workPlace/edit/{workPlaceId}")
    public String edit(Model model, @PathVariable("workPlaceId") Long workPlaceId) {

        if (!model.containsAttribute("workPlaceForm")) {
            model.addAttribute("workPlaceForm", new WorkPlaceForm());
        }

        WorkPlace workPlace = workPlaceService.findById(workPlaceId).orElse(new WorkPlace());
        model.addAttribute("workPlace", workPlace);

        return "worlkPlaces/edit";
    }

    @PostMapping("/workPlace/update")
    public String update(@Validated @ModelAttribute("workPlaceForm") WorkPlaceForm form,
            BindingResult result, RedirectAttributes ra) {

        if (result.hasErrors()) {

            ra.addFlashAttribute("org.springframework.validation.BindingResult.workPlaceForm", result);
            ra.addFlashAttribute("workPlaceForm", form);

            String redirectUrl = UriComponentsBuilder
                    .fromPath("/workPlace/edit/{workPlaceId}")
                    .buildAndExpand(form.getId())
                    .toUriString();
            return "redirect:" + redirectUrl;
        }

        WorkPlace workPlace = workPlaceService.findById(form.getId()).orElse(new WorkPlace());
        workPlace.setName(form.getName());

        // データベースに保存
        workPlaceService.save(workPlace);

        // リダイレクト時にメッセージを追加
        ra.addFlashAttribute("successMessage", "勤務場所名が更新されました。");

        return "redirect:/workPlace/list";
    }
}
