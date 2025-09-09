package com.example.demo.form;

import java.time.LocalDate;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class UserForm implements ValidationGroups {

    private Long id;

    @NotBlank(message = "名前(正式表示)を入力してください。")
    private String fnJp;

    @NotBlank(message = "名前(ひらがな)を入力してください。")
    private String fnJpHira;

    @NotBlank(message = "名前(カタカナ)を入力してください。")
    private String fnJpKata;

    @NotBlank(message = "名前(英語)を入力してください。")
    private String fnEn;

    @NotBlank(message = "姓(正式表示)を入力してください。")
    private String lnJp;

    @NotBlank(message = "姓(ひらがな)を入力してください。")
    private String lnJpHira;

    @NotBlank(message = "姓(カタカナ)を入力してください。")
    private String lnJpKata;

    @NotBlank(message = "姓(英語)を入力してください。")
    private String lnEn;

    // 旧姓の入力が必要な場合にチェック
    private String olnJp;
    private String olnJpHira;
    private String olnJpKata;
    private String olnEn;

    private String mnJp;
    private String mnJpHira;
    private String mnJpKata;
    private String mnEn;

    @NotBlank(message = "メールアドレスを入力してください。")
    @Email(message = "メールアドレスの形式が正しくありません。")
    @Size(max = 255, message = "メールアドレスは255文字以内で入力してください。")
    private String email;

    @NotBlank(message = "パスワードを入力してください。")
    @Size(min = 4, max = 255, message = "パスワードは4文字以上255文字以内で入力してください。")
    private String password;

    @NotNull(message = "社員番号を入力してください。")
    private Long employeeNo;

    private Long currentEmployeeNo;

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

        // 正式表示が入っていたら他の3つも必須にする
        if (isExistOlnJp) {
            if (!isExistOlnJpHira || !isExistOlnJpKata || !isExistOlnEn) {
                return false;
            }
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

        // 正式表示が入っていたら他の3つも必須にする
        if (isExistMnJp) {
            if (!isExistMnJpHira || !isExistMnJpKata || !isExistMnEn) {
                return false;
            }
        }

        return true;
    }

}