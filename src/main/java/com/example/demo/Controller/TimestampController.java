package com.example.demo.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
        
        // timestamps/userlistを表示する
        return "timestamps/userlist";
    }


    // 期間表示
    private String getDateRange() {

        // 今日の日付を取得　(例：2025-09-19)
        LocalDate today = LocalDate.now();
        
        // 今月の初日　(withDayOfMonth(1)：日付を1日に変更する)
        LocalDate start = today.withDayOfMonth(1);
        
        // 今月の末日　(lengthOfMonth：その月が何日あるかを返す)
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        
        // DateTimeFormatterで「2025.09.19」のようにフォーマットを定義
        DateTimeFormatter rangeFmt = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        
        // フォーマットした今月の初日と末日を「2025.09.01 - 2025.09.30」の形式で返却
        return start.format(rangeFmt) + " - " + end.format(rangeFmt);
    }

    
    // 今月の日付一覧を取得
    private List<String> getDates() {

        // 今日の日付を取得　(例：2025-09-19)
        LocalDate today = LocalDate.now();
        
        // 今月の初日　(withDayOfMonth(1)：日付を1日に変更する)
        LocalDate start = today.withDayOfMonth(1);
        
        // 今月の末日　(lengthOfMonth：その月が何日あるかを返す)
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        // DateTimeFormatterで「01(月)」のようにフォーマットを定義
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd(E)", Locale.JAPAN);
        //　フォーマット定義した日付格納リスト
        List<String> formattedDates = new ArrayList<>();
        
        // 今月の1日から月末までの日付を1日ずつループして、整形した文字列をリストに追加する        
        for (LocalDate date = start;  // 月初からスタート
            !date.isAfter(end);       // 月末まで(isAfter：指定した値より後かどうか→前だったらループする)
            date = date.plusDays(1)   // 1日ずつ進める
        ) {
        	// 「01(月)」のように定義したフォーマットでリストに追加
            formattedDates.add(date.format(formatter));
        }
        
        // フォーマットした日付リストの値を返却
        return formattedDates;
    }

    
    // 検索機能
    @PostMapping("/timestamp/userlist/search")
    public String searchUsers(@ModelAttribute SearchForm searchForm, Model model) {

        model.addAttribute("searchForm", searchForm);
        model.addAttribute("departments", departmentService.departmentfindall()); 

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
        
        // 日付一覧メソッド
        model.addAttribute("dateList", getDates());
        
        // 期間表示メソッド
        model.addAttribute("dateRange", getDateRange());
        
        //timestamps/userlistを表示する
        return "timestamps/userlist";
    }


    
    
}
