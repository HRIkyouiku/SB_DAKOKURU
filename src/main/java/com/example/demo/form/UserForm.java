package com.example.demo.form;

import java.time.LocalDate;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class UserForm implements ValidationGroups {

    private Long id;

    //名前

    @NotBlank(message = "名前(正式表示)を入力してください。")
    @Pattern(regexp = "^[^ -~｡-ﾟ]*$", message = "名前(正式表示)は全角で入力してください。")
    @Size(min = 1, max = 255, message = "名前（正式表示）は1文字以上、255文字以内で入力してください。")
    private String fnJp;

    @NotBlank(message = "名前(ひらがな)を入力してください。")
    @Pattern(regexp = "^[あ-んー　]*$", message = "名前(ひらがな）は全角ひらがなで入力してください。")
    @Size(min = 1, max = 255, message = "名前（ひらがな）は1文字以上、255文字以内で入力してください。")
    private String fnJpHira;

    @NotBlank(message = "名前(カタカナ)を入力してください。")
    @Pattern(regexp = "^[ァ-ヶー　]*$", message = "名前(カタカナ)は全角カタカナで入力してください。")
    @Size(min = 1, max = 255, message = "名前（カタカナ）は1文字以上、255文字以内で入力してください。")
    private String fnJpKata;

    @NotBlank(message = "名前(英語)を入力してください。")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "名前(英語)は半角英字で入力してください。")
    @Size(min = 1, max = 255, message = "名前（英語）は1文字以上、255文字以内で入力してください。")
    private String fnEn;

    
    //姓

    @NotBlank(message = "姓(正式表示)を入力してください。")
    @Pattern(regexp = "^[^ -~｡-ﾟ]*$", message = "姓(正式表示)は全角で入力してください。")
    @Size(min = 1, max = 255, message = "姓(正式表示)は1文字以上、255文字以内で入力してください。")
    private String lnJp;

    @NotBlank(message = "姓(ひらがな)を入力してください。")
    @Pattern(regexp = "^[あ-んー　]*$", message = "姓(ひらがな）は全角ひらがなで入力してください。")
    @Size(min = 1, max = 255, message = "姓（ひらがな）は1文字以上、255文字以内で入力してください。")
    private String lnJpHira;

    @NotBlank(message = "姓(カタカナ)を入力してください。")
    @Pattern(regexp = "^[ァ-ヶー　]*$", message = "姓(カタカナ)は全角カタカナで入力してください。")
    @Size(min = 1, max = 255, message = "姓(カタカナ)は1文字以上、255文字以内で入力してください。")
    private String lnJpKata;

    @NotBlank(message = "姓(英語)を入力してください。")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "姓(英語)は半角英字で入力してください。")
    @Size(min = 1, max = 255, message = "姓（英語）は1文字以上、255文字以内で入力してください。")
    private String lnEn;

 
    // 旧姓
    
    @Pattern(regexp = "^[^ -~｡-ﾟ]*$", message = "旧姓(正式表示)は全角で入力してください。")
    private String olnJp;
    
    //値が入っていた場合のみ文字数チェック
    @AssertTrue(message = "旧姓(正式表示)は1文字以上255文字以内で入力してください。")
    public boolean isOlnJpSizeValid() {
        if (olnJp == null || olnJp.trim().isEmpty()) {
            return true; // 空欄や未入力の場合はスルー（任意扱い）
        }

        int len = olnJp.length();
        return len >= 1 && len <= 255;
    }

    @Pattern(regexp = "^[あ-んー　]*$", message = "旧姓(ひらがな）は全角ひらがなで入力してください。")
    private String olnJpHira;
 
    //値が入っていた場合のみ文字数チェック
    @AssertTrue(message = "旧姓(ひらがな)は1文字以上255文字以内で入力してください。")
    public boolean isOlnJpHiraSizeValid() {
        if (olnJpHira == null || olnJpHira.trim().isEmpty()) {
            return true; // 空欄や未入力の場合はスルー（任意扱い）
        }

        int len = olnJpHira.length();
        return len >= 1 && len <= 255;
    }

    @Pattern(regexp = "^[ァ-ヶー　]*$", message = "旧姓(カタカナ)は全角カタカナで入力してください。")
    private String olnJpKata;

    //値が入っていた場合のみ文字数チェック
    @AssertTrue(message = "旧姓(カタカナ)は1文字以上、255文字以内で入力してください。")
    public boolean isOlnJpKataSizeValid() {
        if (olnJpKata == null || olnJpKata.trim().isEmpty()) {
            return true; // 空欄や未入力の場合はスルー（任意扱い）
        }

        int len = olnJpKata.length();
        return len >= 1 && len <= 255;
    }

    @Pattern(regexp = "^[a-zA-Z ]*$", message = "旧姓(英語)は半角で入力してください。")
    private String olnEn;
    
    //値が入っていた場合のみ文字数チェック
    @AssertTrue(message = "旧姓(英語)は1文字以上、255文字以内で入力してください。")
    public boolean isOlnEnSizeValid() {
        if (olnEn == null || olnEn.trim().isEmpty()) {
            return true; // 空欄や未入力の場合はスルー（任意扱い）
        }

        int len = olnEn.length();
        return len >= 1 && len <= 255;
    }

    
    //ミドルネーム

    @Pattern(regexp = "^[^ -~｡-ﾟ]*$", message = "ミドルネーム(正式表示)は全角で入力してください。")
    private String mnJp;
    
    //値が入っていた場合のみ文字数チェック
    @AssertTrue(message = "ミドルネーム(正式表示)は1文字以上、255文字以内で入力してください。")
    public boolean isMnJpSizeValid() {
        if (mnJp == null || mnJp.trim().isEmpty()) {
            return true; // 空欄や未入力の場合はスルー（任意扱い）
        }

        int len = mnJp.length();
        return len >= 1 && len <= 255;
    }
    
    @Pattern(regexp = "^[あ-んー　]*$", message = "ミドルネーム(ひらがな）は全角ひらがなで入力してください。")
    private String mnJpHira;

    //値が入っていた場合のみ文字数チェック
    @AssertTrue(message = "ミドルネーム（ひらがな）は1文字以上、255文字以内で入力してください。")
    public boolean isMnJpHiraSizeValid() {
        if (mnJpHira == null || mnJpHira.trim().isEmpty()) {
            return true; // 空欄や未入力の場合はスルー（任意扱い）
        }

        int len = mnJpHira.length();
        return len >= 1 && len <= 255;
    }
    
    
    @Pattern(regexp = "^[ァ-ヶー　]*$", message = "ミドルネーム(カタカナ)は全角カタカナで入力してください。")
    private String mnJpKata;

    //値が入っていた場合のみ文字数チェック
    @AssertTrue(message = "旧姓(カタカナ)は1文字以上、255文字以内で入力してください。")
    public boolean isMnJpKataSizeValid() {
        if (mnJpKata == null || mnJpKata.trim().isEmpty()) {
            return true; // 空欄や未入力の場合はスルー（任意扱い）
        }

        int len = mnJpKata.length();
        return len >= 1 && len <= 255;
    }
    
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "ミドルネーム(英語)は半角で入力してください。")
    private String mnEn;
    
    //値が入っていた場合のみ文字数チェック
    @AssertTrue(message = "ミドルネーム（英語）は1文字以上、255文字以内で入力してください。")
    public boolean isMnEnSizeValid() {
        if (mnEn == null || mnEn.trim().isEmpty()) {
            return true; // 空欄や未入力の場合はスルー（任意扱い）
        }

        int len = mnEn.length();
        return len >= 1 && len <= 255;
    }
    
    @NotBlank(message = "メールアドレスを入力してください。")
    @Email(message = "メールアドレスは正しい形式で入力してください。")
    @Size(min = 1, max = 255, message = "メールアドレスは1文字以上、255文字以内で入力してください。")
    private String email;

    @NotBlank(message = "パスワードを入力してください。")
    @Size(min = 8, max = 255, message = "パスワードは8文字以上255文字以内で入力してください。")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "パスワードは半角英数字で入力してください。")
    private String password;


    //社員番号

    @NotNull(message = "社員番号を入力してください。")
    private Integer employeeNo;

    private Integer currentEmployeeNo;

    //入社日

    @NotNull(message = "入社日を入力してください。")
    private LocalDate joiningDate;

    private Boolean englishNotation;


    // 旧姓フィールド 入力チェック

    // 旧姓フィールドのいずれかが入力された場合は、olnJp も必須
    @AssertTrue(message = "旧姓の各欄に一つでも入力があった場合は必須です。")
    public boolean isOldNameJpValid() {
        if ((olnJpHira != null && !olnJpHira.isEmpty()) ||
                (olnJpKata != null && !olnJpKata.isEmpty()) ||
                (olnEn != null && !olnEn.isEmpty())) {
            return olnJp != null && !olnJp.isEmpty();
        }
        return true;
    }
    
    // 旧姓フィールドのいずれかが入力された場合は、olnJpHira も必須
    @AssertTrue(message = "旧姓の各欄に一つでも入力があった場合は必須です。")
    public boolean isOldNameHiraValid() {
        if ((olnJp != null && !olnJp.isEmpty()) ||
                (olnJpKata != null && !olnJpKata.isEmpty()) ||
                (olnEn != null && !olnEn.isEmpty())) {
            return olnJpHira != null && !olnJpHira.isEmpty();
        }
        return true;
    }
    
    // 旧姓フィールドのいずれかが入力された場合は、olnJpKata も必須
    @AssertTrue(message = "旧姓の各欄に一つでも入力があった場合は必須です。")
    public boolean isOldNameKataValid() {
        if ((olnJp != null && !olnJp.isEmpty()) ||
                (olnJpHira != null && !olnJpHira.isEmpty()) ||
                (olnEn != null && !olnEn.isEmpty())) {
            return olnJpKata != null && !olnJpKata.isEmpty();
        }
        return true;
    }
    
    // 旧姓フィールドのいずれかが入力された場合は、olnEn も必須
    @AssertTrue(message = "旧姓の各欄に一つでも入力があった場合は必須です。")
    public boolean isOldNameEnValid() {
        if ((olnJp != null && !olnJp.isEmpty()) ||
                (olnJpHira != null && !olnJpHira.isEmpty()) ||
                (olnJpKata != null && !olnJpKata.isEmpty())) {
            return olnEn != null && !olnEn.isEmpty();
        }
        return true;
    }

    
    // ミドルネーム 入力チェック

    // ミドルネームフィールドのいずれかが入力された場合は、mnJp も必須
    @AssertTrue(message = "ミドルネームの各欄に一つでも入力があった場合は必須です。")
    public boolean isMnNameValid() {
        if ((mnJpHira != null && !mnJpHira.isEmpty()) ||
                (mnJpKata != null && !mnJpKata.isEmpty()) ||
                (mnEn != null && !mnEn.isEmpty())) {
            return mnJp != null && !mnJp.isEmpty();
        }
        return true;
    }

    // ミドルネームフィールドのいずれかが入力された場合は、mnJpHira も必須
    @AssertTrue(message = "ミドルネームの各欄に一つでも入力があった場合は必須です。")
    public boolean isMnNameHiraValid() {
        if ((mnJp != null && !mnJp.isEmpty()) ||
                (mnJpKata != null && !mnJpKata.isEmpty()) ||
                (mnEn != null && !mnEn.isEmpty())) {
            return mnJpHira != null && !mnJpHira.isEmpty();
        }
        return true;
    }
    
    // ミドルネームフィールドのいずれかが入力された場合は、mnJpKata も必須
    @AssertTrue(message = "ミドルネームの各欄に一つでも入力があった場合は必須です。")
    public boolean isMnNameKataValid() {
        if ((mnJp != null && !mnJp.isEmpty()) ||
                (mnJpHira != null && !mnJpHira.isEmpty()) ||
                (mnEn != null && !mnEn.isEmpty())) {
            return mnJpKata != null && !mnJpKata.isEmpty();
        }
        return true;
    }
    
    // ミドルネームフィールドのいずれかが入力された場合は、mnEn も必須
    @AssertTrue(message = "ミドルネームの各欄に一つでも入力があった場合は必須です。")
    public boolean isMnNameEnValid() {
        if ((mnJp != null && !mnJp.isEmpty()) ||
                (mnJpHira != null && !mnJpHira.isEmpty()) ||
                (mnJpKata != null && !mnJpKata.isEmpty())) {
            return mnEn != null && !mnEn.isEmpty();
        }
        return true;
    }
}
