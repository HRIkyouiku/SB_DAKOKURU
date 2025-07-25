package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	public String storeDepartment(
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
			// サービス層の作成メソッドを呼び出す
			departmentService.createDepartment(department);
			redirectAttributes.addFlashAttribute("successMessage", "登録しました。");
			return "redirect:/department/index"; // 成功したら一覧画面にリダイレクト
		} catch (IllegalArgumentException e) {
			// サービス層からのビジネスロジックエラー（例：部署名重複）
			model.addAttribute("errorMessage", e.getMessage()); // エラーメッセージをModelに追加
			model.addAttribute("department", department); // 入力値を保持してフォームに戻す
			return "department/create"; // エラー表示のため、再度フォームへ
		}
	}

	// 部署編集・削除画面
	@GetMapping("/department/edit/{id}")
	public String showDepartmentEditForm(
			@PathVariable Long id,
			Model model,
			RedirectAttributes redirectAttributes // RedirectAttributes を追加
	) {
		// IDから部署情報を取得
		Optional<Department> departmentOptional = departmentService.findDepartmentById(id);

		if (departmentOptional.isPresent()) {
			Department department = departmentOptional.get();
			model.addAttribute("department", department); // 編集フォームにバインドされるオブジェクト（初期値として現在のデータが設定される）
			model.addAttribute("originalDepartment", department); // ★変更前を表示するための元の部署データ
			return "department/edit";
		} else {
			// IDが見つからない場合のエラーハンドリング
			model.addAttribute("errorMessage", "指定された部署が見つかりませんでした。");
			return "redirect:/department/index"; // またはエラーページ
		}
	}

	@PostMapping("/department/update/{id}")
	public String updateDepartment(
			@PathVariable Long id,
			@Valid @ModelAttribute Department department, // フォームからの入力値がバインドされる
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		// IDがフォームから送られてこない（パス変数で取得）ため、明示的にDepartmentオブジェクトに設定し直す
		// これがないと、departmentオブジェクトのidがnullになり、更新対象を見失う可能性があります
		if (department.getId() == null) {
			department.setId(id); // パス変数のIDをDepartmentオブジェクトに設定
		} else if (!department.getId().equals(id)) {
			// パス変数のIDとフォームのIDが異なる場合のハンドリング
			model.addAttribute("errorMessage", "リクエストされたIDとフォームのIDが一致しません。");
			// エラー時も元の部署データが必要になるため取得
			model.addAttribute("originalDepartment", departmentService.findById(id).orElse(new Department()));
			model.addAttribute("department", department); // ユーザーが入力した値
			return "department/edit";
		}

		// 1. バリデーションチェック
		if (result.hasErrors()) {
			// バリデーションエラーがある場合、元の入力値を保持して編集画面に戻る
			model.addAttribute("department", department); // ユーザーが入力した値（エラーのあるDepartmentオブジェクト）をModelに再設定
			// ★変更前を表示するための元の部署データを再取得してModelに追加
			model.addAttribute("originalDepartment", departmentService.findById(id).orElse(new Department()));
			return "department/edit"; // 編集画面のHTMLテンプレートに戻る
		}

		try {
			// 2. サービス層を呼び出して部署情報を更新
			departmentService.updateDepartment(id, department); // idと更新対象のdepartmentを渡す

			// 3. 更新成功時、一覧画面にリダイレクトし、成功メッセージを渡す
			redirectAttributes.addFlashAttribute("successMessage", "更新しました。");
			return "redirect:/department/index"; // 部署一覧画面へリダイレクト
		} catch (IllegalArgumentException e) {
			// 4. ビジネスロジックエラー（例: 部署名の重複チェック失敗など）が発生した場合
			model.addAttribute("errorMessage", e.getMessage()); // エラーメッセージをModelに追加
			model.addAttribute("department", department); // ユーザーが入力した値（エラー時の値）を保持してフォームに戻る
			// ★変更前を表示するための元の部署データを再取得してModelに追加
			model.addAttribute("originalDepartment", departmentService.findById(id).orElse(new Department()));
			return "department/edit"; // エラー表示のため、再度編集画面へ
		} catch (Exception e) {
			// その他の予期せぬエラーが発生した場合
			model.addAttribute("errorMessage", "部署の更新中に予期せぬエラーが発生しました: " + e.getMessage());
			model.addAttribute("department", department);
			// ★変更前を表示するための元の部署データを再取得してModelに追加
			model.addAttribute("originalDepartment", departmentService.findById(id).orElse(new Department()));
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
