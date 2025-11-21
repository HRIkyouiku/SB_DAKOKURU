package com.example.demo.form;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class UserForm implements ValidationGroups {

    private Long id;

    @NotBlank(message = "名前(正式表示)を入力してください。")
    @Pattern(
  		  regexp = "^[\\u3040-\\u309F\\u30A0-\\u30FF\\u4E00-\\u9FFF]+$",
  		  message = "名前(正式表示)は全角で入力してください。"
  		)
    @Size(min=1, max=255, message="名前（正式表示）は1文字以上、255文字以内で入力してください。")
    @Column(name = "fn_jp")
    private String fnJp;

    @NotBlank(message = "名前(ひらがな)を入力してください。")
    @Pattern(
  		  regexp = "^[\\u3040-\\u309F]+$",
  		  message = "名前(ひらがな）は全角ひらがなで入力してください。"
  		)
    @Size(min=1, max=255, message="名前（ひらがな）は1文字以上、255文字以内で入力してください。")
    @Column(name = "fn_jp_hira")
    private String fnJpHira;

    @NotBlank(message = "名前(カタカナ)を入力してください。")
    @Pattern(
  		  regexp = "^[\\u30A0-\\u30FF]+$",
  		  message = "名前(カタカナ)は全角カタカナで入力してください。"
  		)
    @Size(min=1, max=255, message="名前（カタカナ）は1文字以上、255文字以内で入力してください。")
    private String fnJpKata;

    @NotBlank(message = "名前(英語)を入力してください。")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "名前(英語)は半角英字で入力してください。")
    @Size(min=1, max=255, message="名前（英語）は1文字以上、255文字以内で入力してください。")
    private String fnEn;

    @NotBlank(message = "姓(正式表示)を入力してください。")
    @Pattern(
  		  regexp = "^[\\u3040-\\u309F\\u30A0-\\u30FF\\u4E00-\\u9FFF]+$",
  		  message = "姓(正式表示)は全角で入力してください。"
  		)
    @Size(min=1, max=255, message="姓（正式表示）は1文字以上、255文字以内で入力してください。")
    private String lnJp;

    @NotBlank(message = "姓(ひらがな)を入力してください。")
    @Pattern(
  		  regexp = "^[\\u3040-\\u309F]+$",
  		  message = "姓(ひらがな）は全角ひらがなで入力してください。"
  		)
    @Size(min=1, max=255, message="姓（ひらがな）は1文字以上、255文字以内で入力してください。")
    private String lnJpHira;

    @NotBlank(message = "姓(カタカナ)を入力してください。")
    @Pattern(
  		  regexp = "^[\\u30A0-\\u30FF]+$",
  		  message = "姓(カタカナ)は全角カタカナで入力してください。"
  		)
    @Size(min=1, max=255, message="姓（カタカナ）は1文字以上、255文字以内で入力してください。")
    private String lnJpKata;

    @NotBlank(message = "姓(英語)を入力してください。")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "姓(英語)は半角英字で入力してください。")
    @Size(min=1, max=255, message="姓（英語）は1文字以上、255文字以内で入力してください。")
    private String lnEn;

    // 旧姓の入力が必要な場合にチェック
    @Pattern(
    		  regexp = "^[\\u3040-\\u309F\\u30A0-\\u30FF\\u4E00-\\u9FFF]+$",
    		  message = "旧姓(正式表示)は全角で入力してください。"
    		)
    @Size(min=1, max=255, message="旧姓（正式表示）は1文字以上、255文字以内で入力してください。")
    private String olnJp;
    
    @Pattern(
    		  regexp = "^[\\u3040-\\u309F]+$",
    		  message = "旧姓(ひらがな）は全角ひらがなで入力してください。"
    		)
    @Size(min=1, max=255, message="旧姓（ひらがな）は1文字以上、255文字以内で入力してください。")
    private String olnJpHira;
    
    @Pattern(
    		  regexp = "^[\\u30A0-\\u30FF]+$",
    		  message = "旧姓(カタカナ)は全角カタカナで入力してください。"
    		)
    @Size(min=1, max=255, message="旧姓(カタカナ)は全角カタカナで入力してください。")
    private String olnJpKata;
    
    @Pattern(regexp = "^[a-zA-Z]+$", message = "旧姓(英語)は半角英字で入力してください。")
    @Size(min=1, max=255, message="旧姓(英語)は1文字以上、255文字以内で入力してください。")
    private String olnEn;

    @Pattern(
  		  regexp = "^[\\u3040-\\u309F\\u30A0-\\u30FF\\u4E00-\\u9FFF]+$",
  		  message = "ミドルネーム(正式表示)は全角で入力してください。"
  		)
    @Size(min=1, max=255, message="ミドルネーム（正式表示）は1文字以上、255文字以内で入力してください。")
    private String mnJp;
    
    @Pattern(
  		  regexp = "^[\\u3040-\\u309F]+$",
  		  message = "ミドルネーム(ひらがな）は全角ひらがなで入力してください。"
  		)
    @Size(min=1, max=255, message="ミドルネーム（ひらがな）は1文字以上、255文字以内で入力してください。")
    private String mnJpHira;
    
    @Pattern(
  		  regexp = "^[\\u30A0-\\u30FF]+$",
  		  message = "ミドルネーム(カタカナ)は全角カタカナで入力してください。"
  		)
    @Size(min=1, max=255, message="ミドルネーム(カタカナ)は1文字以上、255文字以内で入力してください。")
    private String mnJpKata;
    
    @Pattern(regexp = "^[a-zA-Z]+$", message = "ミドルネーム(英語)は半角英字で入力してください。")
    @Size(min=1, max=255, message="ミドルネーム(英語)は1文字以上、255文字以内で入力してください。")
    private String mnEn;

    @NotBlank(message = "メールアドレスを入力してください。")
    @Email(message = "メールアドレスは正しい形式で入力してください。")
    @Size(min=1, max = 255, message = "メールアドレスは1文字以上、255文字以内で入力してください。")
    private String email;

    @NotBlank(message = "パスワードを入力してください。")
    @Size(min = 8, max = 255, message = "パスワードは8文字以上、255文字以内で入力してください。")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "パスワードは半角英数字で入力してください。")
    private String password;

    @NotBlank(message = "社員番号を入力してください。")
    @Size(min = 1, max = 10, message = "社員番号は1桁以上、10桁以内で入力してください。")
    @Pattern(regexp = "^[0-9]+$", message = "社員番号は半角数字で入力してください。")
    private String employeeNo;

    private Long currentEmployeeNo;

    @NotNull(message = "入社日を入力してください。")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    @AssertTrue(message = "ミドルネームの各欄に一つでも入力があった場合は必須です。")
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