package com.example.demo.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Timestamp;
import com.example.demo.entity.WorkPlace;
import com.example.demo.form.TimestampForm;
import com.example.demo.form.UserTimestampForm;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.NameService;
import com.example.demo.service.TimestampService;
import com.example.demo.service.UserService;
import com.example.demo.service.WorkPlaceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TimestampController {

    private final WorkPlaceService workPlaceService;
    private final TimestampService timestampService;
    private final NameService nameService;
    private final UserService userService;

    @GetMapping("/timestamp/create")
    public String timeline(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (!model.containsAttribute("timestampForm")) {
            model.addAttribute("timestampForm", new TimestampForm());
        }

        List<WorkPlace> places = workPlaceService.findAll();
        model.addAttribute("places", places);

        List<Timestamp> timestampHistories = timestampService.findAllByUserIdOrderByCreatedAtDesc(userDetails.getId());
        model.addAttribute("timestampHistories", timestampHistories);
        System.out.println(timestampHistories);

        return "timestamps/create";
    }

    @PostMapping("/timestamp/store")
    public String store(@Validated @ModelAttribute("timestampForm") TimestampForm form,
            BindingResult result, RedirectAttributes ra,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (result.hasErrors()) {
            ra.addFlashAttribute("org.springframework.validation.BindingResult.timestampForm", result);
            ra.addFlashAttribute("timestampForm", form);
            return "redirect:/timestamp/create";
        }

        // 現在の日時を取得
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalTime currentTime = LocalTime.of(currentDateTime.getHour(), currentDateTime.getMinute());
        System.out.println(currentTime);
        // 05:00をリミット時間として定義
        LocalTime limitTime = LocalTime.of(5, 0);
        if (currentTime.isBefore(limitTime)) {
            currentDateTime = currentDateTime.minusDays(1);
        }

        // 日付部分だけを取得
        LocalDate fixedDate = currentDateTime.toLocalDate();

        Timestamp timestamp = new Timestamp();
        timestamp.setUserId(userDetails.getId());
        timestamp.setDate(fixedDate);
        timestamp.setTime(currentTime);
        timestamp.setType(form.getType());
        timestamp.setWorkPlaceId(form.getWorkPlaceId());
        timestamp.setRemark(null); // 備考は未使用
        timestamp.setApproved(false); // 未承認で固定

        // データベースに保存
        timestampService.save(timestamp);

        // リダイレクト時にメッセージを追加
        ra.addFlashAttribute("successMessage", "打刻が登録されました。");

        return "redirect:/timestamp/create";
    }

    @GetMapping("/users_daily_timestamps/{period}")
    public String getDailyTimestampByUsers(@PathVariable("period") String period,
            Model model, @ModelAttribute("timestampForm") UserTimestampForm form) {

        // "yyyy-MM" 形式のパラメータを YearMonth に変換
        YearMonth ym = YearMonth.parse(period, DateTimeFormatter.ofPattern("yyyy-MM"));

        // 月初・月末を取得
        LocalDate firstDay = ym.atDay(1);
        LocalDate lastDay = ym.atEndOfMonth();

        // フォーマットを定義
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        // フォーマットを適用
        String formattedFirstDay = firstDay.format(formatter);
        String formattedLastDay = lastDay.format(formatter);

        // Thymeleaf に渡す
        model.addAttribute("ym", ym);
        model.addAttribute("firstDay", formattedFirstDay);
        model.addAttribute("lastDay", formattedLastDay);

        // 検索キーワードからユーザーIDを取得
        List<Long> userIds = nameService.searchUsers(form.getKeyword());

        System.out.println(userIds);
        // サービスを通じて日付ごとの打刻情報を取得
        List<Object[]> timestamps = userService.getDailyTimestamps(userIds, firstDay, lastDay);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(timestamps);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        model.addAttribute("timestamps", timestamps);

        return "timestamps/users_daily_timestamps";
    }
}
