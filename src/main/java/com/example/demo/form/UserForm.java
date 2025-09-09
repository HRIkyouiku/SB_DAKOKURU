package com.example.demo.form;

import java.time.LocalDate;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.validation.annotation.AllOrNothingGroup;
import com.example.demo.validation.annotation.OptionalLength;
import com.example.demo.validation.annotation.OptionalPattern;

import lombok.Data;

@Data
@AllOrNothingGroup(
        fields = {"olnJp", "olnJpHira", "olnJpKata", "olnEn"},
        message = "旧姓の各欄に一つでも入力があった場合は必須です。"
    )
    @AllOrNothingGroup(
        fields = {"mnJp", "mnJpHira", "mnJpKata", "mnEn"},
        message = "ミドルネームの各欄に一つでも入力があった場合は必須です。"
    )
public class UserForm implements ValidationGroups {

    private Long id;

    @NotBlank(message = "名前(正式表示)を入力してください。")
    @Pattern(regexp = "^[^\\x00-\\x7F]+$", message = "名前(正式表示)は全角で入力してください。")
    @Length(min = 1, max = 255, message = "名前（正式表示）は1文字以上、255文字以内で入力してください。")
    private String fnJp;

    @NotBlank(message = "名前(ひらがな)を入力してください。")
    @Pattern(regexp = "^[ぁ-んー]+$", message = "名前(ひらがな）は全角ひらがなで入力してください。")
    @Length(min = 1, max = 255, message = "名前（ひらがな）は1文字以上、255文字以内で入力してください。")
    private String fnJpHira;

    @NotBlank(message = "名前(カタカナ)を入力してください。")
    @Pattern(regexp = "^[ァ-ンー]+$", message = "名前(カタカナ)は全角カタカナで入力してください。")
    @Length(min = 1, max = 255, message = "名前（カタカナ）は1文字以上、255文字以内で入力してください。")
    private String fnJpKata;

    @NotBlank(message = "名前(英語)を入力してください。")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "名前(英語)は半角英字で入力してください。")
    @Length(min = 1, max = 255, message = "名前（英語）は1文字以上、255文字以内で入力してください。")
    private String fnEn;

    @NotBlank(message = "姓(正式表示)を入力してください。")
    @Pattern(regexp = "^[^\\x00-\\x7F]+$", message = "姓(正式表示)は全角で入力してください。")
    @Length(min = 1, max = 255, message = "姓（正式表示）は1文字以上、255文字以内で入力してください。")
    private String lnJp;

    @NotBlank(message = "姓(ひらがな)を入力してください。")
    @Pattern(regexp = "^[ぁ-んー]+$", message = "姓(ひらがな）は全角ひらがなで入力してください。")
    @Length(min = 1, max = 255, message = "姓（ひらがな）は1文字以上、255文字以内で入力してください。")
    private String lnJpHira;

    @NotBlank(message = "姓(カタカナ)を入力してください。")
    @Pattern(regexp = "^[ァ-ンー]+$", message = "姓(カタカナ)は全角カタカナで入力してください。")
    @Length(min = 1, max = 255, message = "姓（カタカナ）は1文字以上、255文字以内で入力してください。")
    private String lnJpKata;

    @NotBlank(message = "姓(英語)を入力してください。")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "姓(英語)は半角英字で入力してください。")
    @Length(min = 1, max = 255, message = "姓（英語）は1文字以上、255文字以内で入力してください。")
    private String lnEn;

    // 旧姓の入力が必要な場合にチェック　-----------------------------------------------------------
    @OptionalPattern(regexp = "^[^\\x00-\\x7F]+$", message = "旧姓(正式表示)は全角で入力してください。")
    @OptionalLength(min = 1, max = 255, message = "旧姓(正式表示)は1文字以上、255文字以内で入力してください。")
    private String olnJp;
    
    @OptionalPattern(regexp = "^[ぁ-んー]+$", message = "旧姓(ひらがな)は全角ひらがなで入力してください。")
    @OptionalLength(min = 1, max = 255, message = "旧姓(ひらがな)は1文字以上、255文字以内で入力してください。")
    private String olnJpHira;
    
    @OptionalPattern(regexp = "^[ァ-ンー]+$", message = "旧姓(カタカナ)は全角カタカナで入力してください。")
    @OptionalLength(min = 1, max = 255, message = "旧姓(カタカナ)は1文字以上、255文字以内で入力してください。")
    private String olnJpKata;
    
    @OptionalPattern(regexp = "^[a-zA-Z]+$", message = "姓(英語)は半角英字で入力してください。")
    @OptionalLength(min = 1, max = 255, message = "旧姓(英語)は1文字以上、255文字以内で入力してください。")
    private String olnEn;

    @OptionalPattern(regexp = "^[^\\x00-\\x7F]+$", message = "ミドルネーム(正式表示)は全角で入力してください。")
    @OptionalLength(min = 1, max = 255, message = "ミドルネーム(正式表示)は1文字以上、255文字以内で入力してください。")
    private String mnJp;
    
    @OptionalPattern(regexp = "^[ぁ-んー]+$", message = "ミドルネーム(ひらがな)は全角ひらがなで入力してください。")
    @OptionalLength(min = 1, max = 255, message = "ミドルネーム(ひらがな)は1文字以上、255文字以内で入力してください。")
    private String mnJpHira;
    
    @OptionalPattern(regexp = "^[ァ-ンー]+$", message = "ミドルネーム(カタカナ)は全角カタカナで入力してください。")
    @OptionalLength(min = 1, max = 255, message = "ミドルネーム(カタカナ)は1文字以上、255文字以内で入力してください。")
    private String mnJpKata;
    
    @OptionalPattern(regexp = "^[a-zA-Z]+$", message = "ミドルネーム(英語)は半角英字で入力してください。")
    @OptionalLength(min = 1, max = 255, message = "ミドルネーム(英語)は1文字以上、255文字以内で入力してください。")
    private String mnEn;

    @NotBlank(message = "メールアドレスを入力してください。")
    @Length(min = 1, max = 255, message = "メールアドレスは1文字以上、255文字以内で入力してください。")
    @Email(message = "メールアドレスは正しい形式で入力してください。")
    private String email;

    @NotBlank(message = "パスワードを入力してください。")
    @Size(min = 8, max = 255, message = "パスワードは8文字以上255文字以内で入力してください。")
    @Pattern(regexp = "^[\\x20-\\x7e]*$", message = "パスワードは半角英数字で入力してください。")
    private String password;

    @NotNull(message = "社員番号を入力してください。")
    private Integer employeeNo;

    private Integer currentEmployeeNo;

    @NotNull(message = "入社日を入力してください。")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate joiningDate;

    private Boolean englishNotation;

    // 旧姓フィールドのいずれかが入力された場合は、olnJp も必須
    @AssertTrue(message = "旧姓の名前(正式表示)を入力してください。")
    public boolean isOldNameValid() {
        if ((olnJpHira != null && !olnJpHira.isEmpty()) ||
            (olnJpKata != null && !olnJpKata.isEmpty()) ||
            (olnEn != null && !olnEn.isEmpty())) {
            return olnJp != null && !olnJp.isEmpty();
        }
        return true;
    }

}
