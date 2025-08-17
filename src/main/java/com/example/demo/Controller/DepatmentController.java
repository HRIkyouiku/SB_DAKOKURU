package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Department;
import com.example.demo.form.DepartmentSearchForm;
import com.example.demo.service.DepartmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DepatmentController {
	private final DepartmentService departmentService;

	//部署一覧画面
	@GetMapping("/department/index")
	public String showDepartmentList(
			@Valid @ModelAttribute("departmentSearchForm") DepartmentSearchForm form,
			BindingResult result,
			Model model) {
		// バリデーションエラーがある場合
		if (result.hasErrors()) {
			// エラーメッセージをModelに追加
			model.addAttribute("departments", departmentService.findAllDepartments()); // エラー時も全件表示など、適切なリストを表示
			return "department/list"; // 部署一覧画面に戻る
		}

		List<Department> departments;
		// フォームからキーワードを取得
		String keyword = form.getKeyword();

		if (keyword != null && !keyword.trim().isEmpty()) {
			departments = departmentService.searchDepartments(keyword);
		} else {
			departments = departmentService.findAllDepartments();
		}
		model.addAttribute("departments", departments);
		return "department/list";
	}

	// 部署新規登録画面
	@GetMapping("/department/create")
	public String showDepartmentCreateForm(Model model) {
		model.addAttribute("department", new Department());
		return "department/create";
	}

	@PostMapping("/department/store")
	public String createDepartment(
			@Valid @ModelAttribute Department department, // フォームデータをDepartmentオブジェクトにバインドし、バリデーションを実行
			BindingResult result, // バリデーション結果を受け取る
			Model model,
			RedirectAttributes redirectAttributes // リダイレクト時にメッセージを渡すため
	) {
		// バリデーションエラーがある場合
		if (result.hasErrors()) {
			model.addAttribute("department", department); // 入力値を保持してフォームに戻す
			// エラーメッセージはThymeleafのth:errorsで表示される
			return "department/create"; // フォームのHTMLテンプレートに戻る
		}

		try {
			departmentService.createDepartment(department);
			redirectAttributes.addFlashAttribute("successMessage", "登録しました。");
			return "redirect:/department/index";
		} catch (IllegalArgumentException e) {
			String errorMessage = e.getMessage();
			String[] errorParts = errorMessage.split("\n");

			for (String part : errorParts) {
				String[] fieldAndMessage = part.split(":", 2);
				if (fieldAndMessage.length == 2) {
					String fieldName = fieldAndMessage[0];
					String errorMsg = fieldAndMessage[1];
					result.addError(new FieldError("department", fieldName, errorMsg));
				} else {
					model.addAttribute("errorMessage", errorMessage);
					break;
				}
			}

			model.addAttribute("department", department);
			return "department/create";
		} catch (Exception e) {
			model.addAttribute("errorMessage", "部署の登録中に予期せぬエラーが発生しました: " + e.getMessage());
			model.addAttribute("department", department);
			return "department/create";
		}
	}

	// 部署編集・削除画面
	@GetMapping("/department/edit/{id}")
    public String showDepartmentEditForm(
            @PathVariable Long id,
            Model model
    ) {
        // IDから部署情報を取得
        Optional<Department> departmentOptional = departmentService.findDepartmentById(id);

        if (departmentOptional.isPresent()) {
            Department department = departmentOptional.get();
            model.addAttribute("department", department);
            model.addAttribute("originalDepartment", department); // 変更前を表示するための元の部署データ
            return "department/edit";
        } else {
            // IDが見つからない場合のエラーハンドリング
            model.addAttribute("errorMessage", "データの取得に失敗しました。");
            return "redirect:/department/index"; // またはエラーページ
        }
    }

	@PostMapping("/department/update/{id}")
    public String updateDepartment(
            @PathVariable Long id,
            @Valid @ModelAttribute Department department,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        // 1. @Validによる入力値バリデーションチェック
        if (result.hasErrors()) {
            // バリデーションエラーがあった場合、元の部署情報をModelに追加して編集画面に戻る
            // originalDepartmentを取得するためのidを、department.idから取得
            model.addAttribute("originalDepartment", departmentService.findDepartmentById(department.getId()).orElse(null));
            return "department/edit";
        }

        try {
            // 2. ビジネスロジック（重複チェックを含む）
            departmentService.updateDepartment(id, department);
            redirectAttributes.addFlashAttribute("successMessage", "更新しました。");
            return "redirect:/department/index";
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();
            String[] errorParts = errorMessage.split("\n");

            for (String part : errorParts) {
                String[] fieldAndMessage = part.split(":", 2);
                if (fieldAndMessage.length == 2) {
                    String fieldName = fieldAndMessage[0];
                    String errorMsg = fieldAndMessage[1];
                    result.addError(new FieldError("department", fieldName, errorMsg));
                } else {
                    result.addError(new ObjectError("department", errorMessage));
                }
            }

            // ビジネスロジックエラーがあった場合、元の部署情報をModelに追加して編集画面に戻る
            model.addAttribute("originalDepartment", departmentService.findDepartmentById(id).orElse(null));
            return "department/edit";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "部署情報の更新中に予期せぬエラーが発生しました: " + e.getMessage());
            model.addAttribute("originalDepartment", departmentService.findDepartmentById(id).orElse(null));
            return "department/edit";
        }
    }
    
	@PostMapping("/department/delete/{id}") // DELETEリクエストを受け付ける
	public String deleteDepartment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		try {
			departmentService.deleteDepartment(id); // サービス層の削除メソッドを呼び出す
			redirectAttributes.addFlashAttribute("successMessage", "削除しました。");
			return "redirect:/department/index"; // 削除成功後、一覧画面へリダイレクト
		} catch (Exception e) { // 削除に失敗した場合（例: 関連データが存在する場合など）
			redirectAttributes.addFlashAttribute("errorMessage", "削除に失敗しました: " + e.getMessage());
			return "redirect:/department/edit/"; // 編集画面に戻す
		}
	}
}
