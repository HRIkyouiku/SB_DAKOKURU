package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Constants {

    //ステータス(ステータスコード, error code, メッセージキー)
    /** 入力値バリデーションチェック問題なしの場合のtempStatus "(メッセージなし)" */
    SUCCESS_INPUT_VALIDATION(-1, "", ""),
    /** 登録処理成功 "登録しました。" */
    SUCCESS_CREATE(1, "", "success.create.01"),
    /** 更新処理成功 "更新しました。" */
    SUCCESS_UPDATE(2,"", "success.update.01"),
    /** 削除処理成功 "削除しました。" */
    SUCCESS_DELETE(3, "", "success.delete.01"),
    /** 登録処理失敗 "登録に失敗しました。" */
    FAILURE_CREATE(11, "", "errormessages.create.01"),
    /** 更新処理失敗 "更新に失敗しました。" */
    FAILURE_UPDATE(12, "", "errormessages.update.01"),
    /** 削除処理失敗 "削除に失敗しました。" */
    FAILURE_DELETE(13, "", "errormessages.delete.01"),
    /** データ取得失敗 "データの取得に失敗しました。" */
    FAILURE_GET_DATA(17, "", "errormessages.getData.GetData"),

    //入力値チェック
    /** 入力値空欄 "部署名を入力してください" */
    VALIDATION_ERROR_BLANK_JP(21, "NotBlank.department.nameJp", "validation.blank.DepartmentName"),
    /** 入力値空欄 "部署名を入力してください" */
    VALIDATION_ERROR_BLANK_EN(22, "NotBlank.department.nameEn", "validation.blank.DepartmentNameEnglish"),
    /** 入力値長さ不正 "部署名(英語)を入力してください" */
    VALIDATION_ERROR_BETWEEN_JP(26, "Size.department.nameJp", "validation.between.DepartmentName"),
    /** 入力値長さ不正 "部署名(英語)を入力してください" */
    VALIDATION_ERROR_BETWEEN_EN(27, "Size.department.nameEn", "validation.between.DepartmentNameEnglish"),
    /** 入力フォーマット不正 "部署名(英語)は半角英数字で入力してください。" */
    VALIDATION_ERROR_FORMAT_EN(28, "Pattern.department.nameEn", "validation.format.DepartmentNameEnglish"),

    //既存チェック
    /** 既存の部署名 "部署名は既に存在しています。" */
    EXISTS_NAME_JP(101, "", "validation.unique.DepartmentName"),
    /** 既存の部署名 "部署名(英語)は既に存在しています。" */
    EXISTS_NAME_EN(102, "", "validation.unique.DepartmentNameEnglish"),

    //processID
    INSERT(1001, "",""),
    UPDATE(1002, "", ""),
    DELETE(1003, "", "");

    private Integer statusCode;
    private String errorCode;
    private String key;

    //ステータスコードから対応するenum定数を逆引きして取得
    public static Constants getByCode (Integer code) {

        for (Constants Constant: Constants.values()) {
            if (Constant.getStatusCode() == code) {
                return Constant;
            }
        }

        return null;
    }

    //エラーコードから対応するenum定数を逆引きして取得
    public static Constants getByCode (String code) {

        for (Constants Constant: Constants.values()) {
            if (Constant.getErrorCode().equals(code)) {
                return Constant;
            }
        }

        return null;
    }

}
