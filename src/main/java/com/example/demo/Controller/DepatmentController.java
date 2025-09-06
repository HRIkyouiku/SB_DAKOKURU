package com.example.demo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.constant.Constants;
import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;
import com.example.demo.utility.Utilities;

import lombok.RequiredArgsConstructor;

@Controller
@PropertySource("classpath:messages.properties")
@RequiredArgsConstructor
public class DepatmentController {

    private final DepartmentService departmentService;
    private final Environment messages;

    /** 部署一覧画面表示 */
    @GetMapping("/department/index")
    public String departmentIndex(@ModelAttribute("department") Department department,
            Model model) {

        List<Department> list = null;

        //部署リスト取得
        //取得に失敗した場合、modelにerrorメッセージを登録する
        try {
            list = departmentService.departmentList();
        } catch(RuntimeException e) {
            model.addAttribute("messageValue",
                    messages.getProperty(Constants.FAILURE_GET_DATA.getKey()));
        }

        //modelに部署リストを登録して画面表示
        model.addAttribute("departmentList", list);
        return "department/index";
    }

    /** 部署名検索 */
    @GetMapping("/department/index/search{searchWord}")
    public String departmentSearch(@RequestParam("searchWord") String searchWord,
            Model model) {

        List<Department> list = null;
        Integer listSize = null;

        //検索文字がnullか空白だった場合検索実行はせず全件表示する
        if (searchWord.isBlank()) {
            //部署リスト取得
            //取得に失敗した場合、modelにerrorメッセージを登録する
            try {
                list = departmentService.departmentList();
            } catch(RuntimeException e) {
                model.addAttribute("messageValue",
                        messages.getProperty(Constants.FAILURE_GET_DATA.getKey()));
            }

            //modelに部署リストを登録して画面表示
            model.addAttribute("departmentList", list);
            return "department/index";
        }

        //部署リスト取得
        //取得に失敗した場合、modelにerrorメッセージを登録する
        try {
            list = departmentService.departmentSearchList(searchWord);
            listSize = list.size();
        } catch(RuntimeException e) {
            model.addAttribute("messageValue",
                    messages.getProperty(Constants.FAILURE_GET_DATA.getKey()));
        }

        //modelに部署リストを登録して画面表示
        model.addAttribute("departmentList", list);
        model.addAttribute("resultSize", listSize);
        model.addAttribute("searchWord", searchWord);

        return "department/index";
    }

    /** 新規部署追加画面表示 */
    @GetMapping("/department/create")
    public String departmentCreate(@ModelAttribute("department") Department department,
            Model model) {

        return "department/create";
    }

    /** 新規部署追加 */
    @PostMapping("/department/store")
    public String departmentStore(
            @ModelAttribute("department") @Validated Department department,
            BindingResult result, Model model, RedirectAttributes redirectAttribute) {

        //部署名(日本語)と部署名(英語)の入力値が不正だった場合最初のエラーメッセージを渡して戻る
        if (result.hasErrors()) {

            //各入力値に関してエラーのフィルター、ソートを行う
            List<Integer> statusCodeNameJp =
                    Utilities.validationFilterSort("nameJp", result.getAllErrors());
            List<Integer> statusCodeNameEn =
                    Utilities.validationFilterSort("nameEn", result.getAllErrors());

            //各フィールドの入力値についてエラーがあったかどうか確認し、存在した場合モデルに登録
            if (statusCodeNameJp.size() != 0) {
                model.addAttribute("validationErrorKeyNameJp",
                        Constants.getByCode(statusCodeNameJp.getFirst()).getKey());
            }
            if (statusCodeNameEn.size() != 0) {
                model.addAttribute("validationErrorKeyNameEn",
                        Constants.getByCode(statusCodeNameEn.getFirst()).getKey());
            }
            model.addAttribute("department", department);
            return "department/create";
        }

        //ステータスコード格納変数
        Integer storeStatus = 0;

        //入力値の検証
        storeStatus = departmentService.checkStoreInput(department);

        //部署名(日本語)と部署名(英語)の入力値が不正だった場合エラーメッセージを渡して戻る
        if (storeStatus == Constants.VALIDATION_ERROR_BLANK_JP.getStatusCode()
                || storeStatus == Constants.VALIDATION_ERROR_BETWEEN_JP.getStatusCode()
                || storeStatus == Constants.VALIDATION_ERROR_BLANK_EN.getStatusCode()
                || storeStatus == Constants.VALIDATION_ERROR_BETWEEN_EN.getStatusCode()
                || storeStatus == Constants.VALIDATION_ERROR_FORMAT_EN.getStatusCode()) {

            //入力値が空白、空文字列、NULLだった場合modelにエラーメッセージと入力値を登録し戻る
            model.addAttribute("validationErrorKey",
                    Constants.getByCode(storeStatus).getKey());
            model.addAttribute("department",
                    department);
            return "department/create";
        } else if (!(storeStatus == Constants.SUCCESS_INPUT_VALIDATION.getStatusCode())) {

            //validationチェック失敗、成功以外のステータスコードだった場合例外にスロー
            throw new RuntimeException();
        }

        //部署登録処理
        try {
            storeStatus = departmentService.storeDepartment(department);
        } catch (RuntimeException e) {

            //登録処理に失敗した場合、入力値とエラーメッセージをmodelに登録して登録画面に戻る
            model.addAttribute("messageValue",
                    messages.getProperty(Constants.FAILURE_CREATE.getKey()));
            model.addAttribute("department",
                    department);
            return "department/create";
        }
        //入力値が既存だった場合エラーメッセージを渡して戻る
        if (storeStatus == Constants.EXISTS_NAME_JP.getStatusCode()
                || storeStatus == Constants.EXISTS_NAME_EN.getStatusCode()) {

            //modelにエラーメッセージと入力された部署名を登録し戻る
            model.addAttribute("validationErrorKey",
                    Constants.getByCode(storeStatus).getKey());
            model.addAttribute("department",
                    department);

            return "department/create";
        }

        //modelに部署リストとメッセージを登録し部署一覧画面に遷移
        redirectAttribute.addFlashAttribute("messageValue",
                messages.getProperty(Constants.getByCode(storeStatus).getKey()));
        model.addAttribute("department", department);

        return "redirect:/department/index";
    }

    /** 部署名変更画面表示 */
    @GetMapping("/department/edit/{departmentId}")
    public String departmentEdit(@PathVariable("departmentId") Long departmentId,
            Model model) {

        //IDからデータを取得し格納する変数
        Department correntDepartment = null;

        //idから部署情報を取得
        //データ取得に失敗した場合、modelにエラーメッセージ追加
        try {
            correntDepartment = departmentService.getDepartment(departmentId);
        } catch (RuntimeException e) {
            model.addAttribute("messageValue",
                    Constants.FAILURE_GET_DATA.getKey());
        }

        //編集する部署情報をmodelに渡して編集ページに遷移
        model.addAttribute("correntDepartment", correntDepartment);
       model.addAttribute("department", correntDepartment);
        return "department/edit";
    }

    /** 部署名更新処理 */
    @PutMapping("/department/update/{departmentId}")
    public String departmentUpdate(@PathVariable("departmentId") Long departmentId,
            @ModelAttribute("department") @Validated Department department,
            BindingResult result,
            Model model, RedirectAttributes redirectAttributes) {

        //ステータスコード格納変数
        Integer updateStatus = 0;

        //変更後の部署にIDを格納
        department.setId(departmentId);

        //修正前の部署名取得、modelに登録
        try {
            Department correntDepartment = departmentService.getDepartment(departmentId);
            model.addAttribute("correntDepartment", correntDepartment);
        } catch (RuntimeException e) {

            //データ取得に失敗した場合、modelにエラーメッセージを登録して編集画面に戻る
            model.addAttribute("correntDepartment", null);
            model.addAttribute("message",
                    messages.getProperty(Constants.FAILURE_GET_DATA.getKey()));
            return "department/edit" + departmentId;
        }

        //部署名(日本語)と部署名(英語)の入力値が不正だった場合最初のエラーメッセージを渡して戻る
        if (result.hasErrors()) {

            //各入力値に関してエラーのフィルター、ソートを行う
            List<Integer> statusCodeNameJp =
                    Utilities.validationFilterSort("nameJp", result.getAllErrors());
            List<Integer> statusCodeNameEn =
                    Utilities.validationFilterSort("nameEn", result.getAllErrors());

            //各フィールドの入力値についてエラーがあったかどうか確認し、存在した場合モデルに登録
            if (statusCodeNameJp.size() != 0) {
                model.addAttribute("validationErrorKeyNameJp",
                        Constants.getByCode(statusCodeNameJp.getFirst()).getKey());
            }
            if (statusCodeNameEn.size() != 0) {
                model.addAttribute("validationErrorKeyNameEn",
                        Constants.getByCode(statusCodeNameEn.getFirst()).getKey());
            }
            model.addAttribute("department", department);
            return "department/edit";
        }

        //validationチェック
        //入力値の検証
        updateStatus = departmentService.checkStoreInput(department);

        //部署名(日本語)と部署名(英語)の入力値が不正だった場合エラーメッセージを渡して戻る
        if (updateStatus == Constants.VALIDATION_ERROR_BLANK_JP.getStatusCode()
                || updateStatus == Constants.VALIDATION_ERROR_BETWEEN_JP.getStatusCode()
                || updateStatus == Constants.VALIDATION_ERROR_BLANK_EN.getStatusCode()
                || updateStatus == Constants.VALIDATION_ERROR_BETWEEN_EN.getStatusCode()
                || updateStatus == Constants.VALIDATION_ERROR_FORMAT_EN.getStatusCode()) {

            //入力値が空白、空文字列、NULLだった場合modelにエラーメッセージと入力値を登録し戻る
            model.addAttribute("validationErrorKey",
                    Constants.getByCode(updateStatus).getKey());
            model.addAttribute("department", department);

            return "department/edit";

        } else if (!(updateStatus == Constants.SUCCESS_INPUT_VALIDATION.getStatusCode())) {

            //validationチェック失敗、成功以外のステータスコードだった場合例外にスロー
            throw new RuntimeException();
        }

        //部署名更新処理
        try {

            updateStatus = departmentService.storeDepartment(department);
        } catch (RuntimeException e) {

            //modelにエラーメッセージを登録して戻る
            model.addAttribute("messageValue",
                    messages.getProperty(Constants.FAILURE_UPDATE.getKey()));
            model.addAttribute("department",
                    department);

            return "department/edit";

        }
        //入力値が既存だった場合エラーメッセージを渡して戻る
        if (updateStatus == Constants.EXISTS_NAME_JP.getStatusCode()
                || updateStatus == Constants.EXISTS_NAME_EN.getStatusCode()) {

            //modelにエラーメッセージと入力された部署名を登録し戻る
            model.addAttribute("validationErrorKey",
                    Constants.getByCode(updateStatus).getKey());
            model.addAttribute("department",
                    department);

            return "department/edit";
        }

        //redirectAttributesに登録成功メッセージを登録し、部署名編集画面に戻る
        redirectAttributes.addFlashAttribute("messageValue",
                messages.getProperty(Constants.SUCCESS_UPDATE.getKey()));
        return "redirect:/department/edit/" + departmentId;
    }

    /** 部署削除処理 */
    @DeleteMapping("/department/delete/{departmentId}")
    public String departmentDelete(@PathVariable("departmentId") Long departmentId,
            @RequestParam("nameJp") String departmentNameJp,
            @RequestParam("nameEn") String departmentNameEn,
            Model model, RedirectAttributes redirectAttribute) {

        //status定義
        Integer deleteStatus = 0;

        //削除処理
        try {
            deleteStatus = departmentService.deleteDepartment(departmentId);
        } catch (RuntimeException e) {
            deleteStatus = Constants.FAILURE_DELETE.getStatusCode();
        }

        //ステータスが成功以外だった場合エラーメッセージをモデルに登録して編集画面に戻る
        if (deleteStatus != Constants.SUCCESS_DELETE.getStatusCode()) {

            //departmentに入力値を格納
            Department department = new Department();
            department.setId(departmentId);
            department.setNameJp(departmentNameJp);
            department.setNameEn(departmentNameEn);

            model.addAttribute("messageValue",
                    messages.getProperty(Constants.getByCode(deleteStatus).getKey()));
            model.addAttribute("department",
                    department);
            return "department/edit";
        }

        //削除成功メッセージを登録して部署一覧表示画面に遷移
        redirectAttribute.addFlashAttribute("messageValue",
                messages.getProperty(Constants.SUCCESS_DELETE.getKey()));
        return "redirect:/department/index";
    }

}
