package com.example.demo.utility;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.validation.ObjectError;

import com.example.demo.constant.Constants;

public class Utilities {

    /** ObjectErrorリストをentity内のフィールド名でフィルターし
     *　 null, pattern, sizeの順にソートし
     *  ステータスコードに変換し
     * 　listに格納しなおしたリストを戻す
     *  該当fieldのエラーが存在しなかった場合は空のlistを戻す */
    public static List<Integer> validationFilterSort (String field,
            List<ObjectError> objectErrors) {

        //errorでソートしエラーコードを格納するlist
        List<Integer> list = new ArrayList<>();
        //第一引数で受け取ったfieldに関するエラーがるか判定するフラグ
        Boolean existErrorFlg = false;
        //ObjectErrorのcodesがエラーコードを格納しているインデックス
        //(種別.entity名.フィールド名)
        final Integer ERROR_CODE_INDEX = 0;

        //第一引数で受け取ったフィールドのエラーがあるかチェック
        for (ObjectError oe: objectErrors) {
            //1つでも該当フィールドのエラーがあった場合はフラグを立てチェックループを抜ける
            if (oe.getCodes()[ERROR_CODE_INDEX].contains(field)) {
                existErrorFlg = true;
                break;
            }
        }

        if (existErrorFlg) {

        //List<ObjectErrors>をfieldでフィルターしlistに変換
        objectErrors.stream().filter(x ->
                x.getCodes()[ERROR_CODE_INDEX].contains(field)
                ).sorted(Comparator.comparing(ObjectError::getCode)
                ).forEach(x ->
                        list.add(
                                Constants.getByCode(
                                        x.getCodes()[ERROR_CODE_INDEX]).getStatusCode())
                );        
        } 

        return list;
    }

}
