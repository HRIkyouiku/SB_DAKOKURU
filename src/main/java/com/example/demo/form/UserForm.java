package com.example.demo.form;

import java.time.LocalDate;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import org.springframework.util.StringUtils;

import lombok.Data;

@Data
public class UserForm implements ValidationGroups {

	private Long id;

	@NotBlank(message = "名前(正式表示)を入力してください。")
	@Size(min = 1, max = 255, message = "名前(正式表示)は、1文字以上、255文字以内で入力してください。")
	private String fnJp;

	@NotBlank(message = "名前(ひらがな)を入力してください。")
	@Size(min = 1, max = 255, message = "名前(ひらがな)は、1文字以上、255文字以内で入力してください。")
	@Pattern(regexp = "^[あ-ん]*$", message = "名前(ひらがな)は全角ひらがなで入力してください。")
	private String fnJpHira;

	@NotBlank(message = "名前(カタカナ)を入力してください。")
	@Size(min = 1, max = 255, message = "名前(カタカナ)は、1文字以上、255文字以内で入力してください。")
	@Pattern(regexp = "^[ア-ン]*$", message = "名前(カタカナ)は全角カタカナで入力してください。")
	private String fnJpKata;

	@NotBlank(message = "名前(英語)を入力してください。")
	@Size(min = 1, max = 255, message = "名前(英語)は、1文字以上、255文字以内で入力してください。")
	@Pattern(regexp = "[a-zA-Z]*", message = "名前(英語)は半角英字で入力してください")
	private String fnEn;

	@NotBlank(message = "姓(正式表示)を入力してください。")
	@Size(min = 1, max = 255, message = "姓(正式表示)は、1文字以上、255文字以内で入力してください。")
	private String lnJp;

	@NotBlank(message = "姓(ひらがな)を入力してください。")
	@Size(min = 1, max = 255, message = "姓(ひらがな)は、1文字以上、255文字以内で入力してください。")
	@Pattern(regexp = "^[あ-ん]*$", message = "姓(ひらがな)は全角ひらがなで入力してください。")
	private String lnJpHira;

	@NotBlank(message = "姓(カタカナ)を入力してください。")
	@Size(min = 1, max = 255, message = "姓(カタカナ)は、1文字以上、255文字以内で入力してください。")
	@Pattern(regexp = "^[ア-ン]*$", message = "姓(カタカナ)は全角カタカナで入力してください。")
	private String lnJpKata;

	@NotBlank(message = "姓(英語)を入力してください。")
	@Size(min = 1, max = 255, message = "姓(英語)は、1文字以上、255文字以内で入力してください。")
	@Pattern(regexp = "[a-zA-Z]*", message = "姓(英語)は半角英字で入力してください")
	private String lnEn;

	// 旧姓の入力が必要な場合にチェック
	private String olnJp;
	@AssertTrue(message = "旧姓(正式表示)は、1文字以上、255文字以内で入力してください。")
	public boolean isOlnJpValid() {
	    if (!StringUtils.hasText(olnJp)) {
	        return true;
	    }
	    return olnJp.length() >= 1 && olnJp.length() <= 255;
	}
	
	@Pattern(regexp = "^[あ-ん]*$", message = "旧姓(ひらがな)は全角ひらがなで入力してください。")
	private String olnJpHira;
	@AssertTrue(message = "旧姓(ひらがな)は、1文字以上、255文字以内で入力してください。")
	public boolean isOlnJpHiraValid() {
	    if (!StringUtils.hasText(olnJpHira)) {
	        return true;
	    }
	    return olnJpHira.length() >= 1 && olnJpHira.length() <= 255;
	}
	
	@Pattern(regexp = "^[ア-ン]*$", message = "旧姓(カタカナ)は全角カタカナで入力してください。")
	private String olnJpKata;
	@AssertTrue(message = "旧姓(カタカナ)は、1文字以上、255文字以内で入力してください。")
	public boolean isOlnJpKataValid() {
	    if (!StringUtils.hasText(olnJpKata)) {
	        return true;
	    }
	    return olnJpKata.length() >= 1 && olnJpKata.length() <= 255;
	}
	
	@Pattern(regexp = "[a-zA-Z]*", message = "旧姓(英語)は半角英字で入力してください")
	private String olnEn;
	@AssertTrue(message = "旧姓(英語)は、1文字以上、255文字以内で入力してください。")
	public boolean isOlnEnValid() {
	    if (!StringUtils.hasText(olnEn)) {
	        return true;
	    }
	    return olnEn.length() >= 1 && olnEn.length() <= 255;
	}

	private String mnJp;
	@AssertTrue(message = "ミドルネーム(正式表示)は、1文字以上、255文字以内で入力してください。")
	public boolean isMnJpValid() {
	    if (!StringUtils.hasText(mnJp)) {
	        return true;
	    }
	    return mnJp.length() >= 1 && mnJp.length() <= 255;
	}

	@Pattern(regexp = "^[あ-んー]*$", message = "ミドルネーム(ひらがな)は全角ひらがなで入力してください。")
	private String mnJpHira;
	@AssertTrue(message = "ミドルネーム(ひらがな)は、1文字以上、255文字以内で入力してください。")
	public boolean isMnJpHiraValid() {
	    if (!StringUtils.hasText(mnJpHira)) {
	        return true;
	    }
	    return mnJpHira.length() >= 1 && mnJpHira.length() <= 255;
	}

	@Pattern(regexp = "^[ア-ンー]*$", message = "ミドルネーム(カタカナ)は全角カタカナで入力してください。")
	private String mnJpKata;
	@AssertTrue(message = "ミドルネーム(カタカナ)は、1文字以上、255文字以内で入力してください。")
	public boolean isMnJpKataValid() {
	    if (!StringUtils.hasText(mnJpKata)) {
	        return true;
	    }
	    return mnJpKata.length() >= 1 && mnJpKata.length() <= 255;
	}

	@Pattern(regexp = "[a-zA-Z]*", message = "ミドルネーム姓(英語)は半角英字で入力してください")
	private String mnEn;
	@AssertTrue(message = "ミドルネーム(英語)は、1文字以上、255文字以内で入力してください。")
	public boolean isMnEnValid() {
	    if (!StringUtils.hasText(mnEn)) {
	        return true;
	    }
	    return mnEn.length() >= 1 && mnEn.length() <= 255;
	}

	@NotBlank(message = "メールアドレスを入力してください。")
	@Email(message = "メールアドレスの形式が正しくありません。")
	@Size(min = 1, max = 255, message = "メールアドレスは1文字以上、255文字以内で入力してください。")
	private String email;

	@NotBlank(message = "パスワードを入力してください。")
	@Size(min = 8, max = 255, message = "パスワードは8文字以上、255文字以内で入力してください。")
	@Pattern(regexp = "[a-zA-Z0-9]*", message = "パスワードは半角英字で入力してください")
	private String password;

	@NotNull(message = "社員番号を入力してください。")
	/*@Pattern(regexp = "^[0-9]+$", message = "社員番号は半角数字で入力してください")*/
	private Integer employeeNo;

	private Integer currentEmployeeNo;

	@NotNull(message = "入社日を入力してください。")
	private LocalDate joiningDate;

	private Boolean englishNotation;

	@AssertTrue(message = "旧姓の各欄に一つでも入力があった場合は必須です。")
	public boolean isOldNameValid() {
		// すべて入力の有無を確認する
		
		boolean isExistOlnJp = (olnJp != null && !olnJp.isEmpty());
		boolean isExistOlnJpHira = (olnJpHira != null && !olnJpHira.isEmpty());
		boolean isExistOlnJpKata = (olnJpKata != null && !olnJpKata.isEmpty());
		boolean isExistOlnEn = (olnEn != null && !olnEn.isEmpty());

		// どれかが入っていたら他の3つも必須にする
		boolean anyExists = isExistOlnJp || isExistOlnJpHira || isExistOlnJpKata || isExistOlnEn;
		boolean allExists = isExistOlnJp && isExistOlnJpHira && isExistOlnJpKata && isExistOlnEn;

		if (anyExists) {
			return allExists;
		}
		
		
		return true;
	}
	
	@AssertTrue(message = "ミドルネームに関わるフォームのうち、一つでも入力があった場合は全てを必須とする。")
	public boolean isMiddleNameValid() {
		// すべて入力の有無を確認する
		boolean isExistMnJp = (mnJp != null && !mnJp.isEmpty());
		boolean isExistMnJpHira = (mnJpHira != null && !mnJpHira.isEmpty());
		boolean isExistMnJpKata = (mnJpKata != null && !mnJpKata.isEmpty());
		boolean isExistMnEn = (mnEn != null && !mnEn.isEmpty());

		// どれかが入っていたら他の3つも必須にする
		boolean anyExists = isExistMnJp || isExistMnJpHira || isExistMnJpKata || isExistMnEn;
		boolean allExists = isExistMnJp && isExistMnJpHira && isExistMnJpKata && isExistMnEn;

		if (anyExists) {
			return allExists;
		}

		return true;
	}

}