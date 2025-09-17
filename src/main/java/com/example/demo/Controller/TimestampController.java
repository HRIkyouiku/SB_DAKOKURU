package com.example.demo.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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

        //検索フォーム初期表示
        model.addAttribute("searchForm", new SearchForm());
        
        //部署一覧の取得
        List<Department> departments = departmentService.departmentfindall();
        model.addAttribute("departments", departments);
 
        //timestamps/userlistを表示する
        return "timestamps/userlist";
    }
    
    //検索機能
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

        
        //timestamps/userlistを表示する
        return "timestamps/userlist";
    }


    
    
}
