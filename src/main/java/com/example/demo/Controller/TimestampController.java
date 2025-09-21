package com.example.demo.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Department;
import com.example.demo.entity.Timestamp;
import com.example.demo.entity.User;
import com.example.demo.entity.WorkPlace;
import com.example.demo.form.SearchForm;
import com.example.demo.form.TimestampForm;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.NameService;
import com.example.demo.service.TimestampService;
import com.example.demo.service.UserService;
import com.example.demo.service.WorkPlaceService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TimestampController {

    private final WorkPlaceService workPlaceService;
    private final TimestampService timestampService;
    private final NameService nameService;
    private final UserService userService;
    private final DepartmentService departmentService;

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
    
    // 「ユーザーごと勤怠一覧」ページ
    
    @GetMapping("/timestamp/userlist")
    public String userlist(Model model, @ModelAttribute SearchForm searchForm) {

        // 検索フォーム初期表示
        model.addAttribute("searchForm", new SearchForm());
        
        // 部署一覧の取得
        List<Department> departments = departmentService.departmentfindall();
        model.addAttribute("departments", departments);
        
        // ページネーションの非表示フラグ
        model.addAttribute("showPagination", false);
     
        // timestamps/userlistを表示する
        return "timestamps/userlist";
    }


    // 期間表示（例：2025.09.01 - 2025.09.30）
    private String getDateRange(int year, int month) {
        // 対象年月の初日と末日を生成
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        // 表示形式（例：2025.09.01 - 2025.09.30）
        DateTimeFormatter rangeFmt = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return start.format(rangeFmt) + " - " + end.format(rangeFmt);
    }

        
    // 検索機能
    @PostMapping("/timestamp/userlist/search")
    public String searchUsers(@ModelAttribute SearchForm searchForm, Model model,
        @RequestParam(required = false) Integer year,
        @RequestParam(required = false) Integer month) {

        model.addAttribute("searchForm", searchForm);
        model.addAttribute("departments", departmentService.departmentfindall()); 
        
        // ページネーションの表示フラグ
        model.addAttribute("showPagination", true);

        // 入力された検索条件
        String name = searchForm.getSearchName();
        Long departmentId = searchForm.getDepartmentId();

        //検索メソッド
        List<User> users ;

        // １.名前なし＋部署なし
        if ((name == null || name.isEmpty()) && departmentId == null) {
            // 全ユーザー検索
            users = userService.userlistfindall();
        // ２.名前あり＋部署なし
        } else if ((name != null || !name.isEmpty()) && departmentId == null) {
            // ユーザー名のみで検索
            users = userService.userlistfindByName(name);
            // ３.名前なし＋部署あり
        } else if ((name == null || name.isEmpty()) && departmentId != null) {
            // 部署IDのみで検索
            users = userService.userlistfindByDepartmentId(departmentId);
            // ４.１～３以外(名前あり＋部署あり)
        } else {
        	// 名前と部署IDで検索
        	users = userService.userlistfindByNameAndDepartmentId(name, departmentId);
        }
        
        model.addAttribute("users", users);
                 
        //　ページネーション
        int targetYear = (year != null) ? year : LocalDate.now().getYear();
        int targetMonth = (month != null) ? month : LocalDate.now().getMonthValue();
        
        // 先月・翌月の計算
        int prevMonth = (targetMonth == 1) ? 12 : targetMonth - 1;
        int prevYear  = (targetMonth == 1) ? targetYear - 1 : targetYear;
        int nextMonth = (targetMonth == 12) ? 1 : targetMonth + 1;
        int nextYear  = (targetMonth == 12) ? targetYear + 1 : targetYear;

        model.addAttribute("year", targetYear);
        model.addAttribute("month", targetMonth);
        model.addAttribute("prevYear", prevYear);
        model.addAttribute("prevMonth", prevMonth);
        model.addAttribute("nextYear", nextYear);
        model.addAttribute("nextMonth", nextMonth);

        // 期間表示メソッド
        String dateRange = getDateRange(targetYear, targetMonth);
        model.addAttribute("dateRange", dateRange);

        //勤怠データ取得
        // 今日の日付を取得　(例：2025-09-19)
        LocalDate today = LocalDate.now();
        
        // 今月の初日　(withDayOfMonth(1)：日付を1日に変更する)
        LocalDate start = LocalDate.of(targetYear, targetMonth, 1);
        
        // 今月の末日　(lengthOfMonth：その月が何日あるかを返す)
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        // 日付リスト（LocalDate型）を生成
        List<LocalDate> rawDateList = new ArrayList<>();
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            rawDateList.add(date);
        }
        model.addAttribute("rawDateList", rawDateList);

        // 勤怠時間取得        
        // 時刻を "HH:mm" 形式（例：09:00）で表示するためのフォーマッターを定義
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        
        // 検索結果で取得した全ユーザーに対してループ
        for (User user : users) {
            
            // 対象ユーザーの今月分の勤怠データ（出退勤）を取得
            List<Timestamp> timestamps = timestampService.findByUserIdAndDateBetween(user.getId(), start, end);

            // ユーザーに勤怠データをセット（テンプレートで使うため）
            user.setTimestamps(timestamps);

            // 日付ごとの表示用文字列（出：HH:mm / 退：HH:mm）を格納
            List<String> displayList = new ArrayList<>();

            // 今月の日付リスト（rawDateList）をループ
            for (LocalDate date : rawDateList) {
            	// 出勤時間（type = 1）を抽出（該当がなければ空）
                Optional<LocalTime> shukkin = timestamps.stream()
                    .filter(ts -> ts.getDate().equals(date) && ts.getType() == 1)
                    .map(Timestamp::getTime)
                    .findFirst();

                // 退勤時間（type = 4）を抽出（該当がなければ空）
                Optional<LocalTime> taikin = timestamps.stream()
                    .filter(ts -> ts.getDate().equals(date) && ts.getType() == 4)
                    .map(Timestamp::getTime)
                    .findFirst();

                // 出勤時間が存在する場合は "出：HH:mm"、なければ赤文字で "出：-"
                String shukkinStr = shukkin.isPresent()
                    ? "出：" + shukkin.get().format(formatter)
                    : "<span style='color:red;'>出：-</span>";
                
                // 退勤時間が存在する場合は "退：HH:mm"、なければ赤文字で "退：-"
                String taikinStr = taikin.isPresent()
                    ? "退：" + taikin.get().format(formatter)
                    : "<span style='color:red;'>退：-</span>";

                // 出退勤を1つの文字列にまとめてリストに追加
                displayList.add(shukkinStr + "<br>" + taikinStr);
            }

         // 表示用リストをユーザーにセット（Userクラスに @Transient フィールドとして定義）
            user.setAttendanceDisplay(displayList);
        }
             
        //timestamps/userlistを表示する
        return "timestamps/userlist";
    }

}
