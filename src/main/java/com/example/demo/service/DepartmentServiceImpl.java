package com.example.demo.service;

import java.text.Collator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.constant.Constants;
import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    /** 部署名リスト取得 */
    @Override
    public List<Department> departmentList(){

        //全件取得しlistに格納
        List<Department> list = null;
        list = departmentRepository.findAll();

        //部署リストを部署名(日本語)で降順にソート
        list.sort(
                (a, b) ->
                Collator.getInstance().compare(b.getNameJp(), a.getNameJp())
                );

        return list;
    }

    /** 部署検索処理 */
    public List<Department> departmentSearchList(String searchWord){

        //検索結果部署格納用list
        List<Department> list = null;
        HashSet<Department> set = new HashSet<>();

        //検索処理
        try {

            //部署名(日本語)の検索結果を格納
            set.addAll(departmentRepository.findAllByNameJpLike("%" + searchWord + "%"));

            //部署名(英語)の検索結果を格納
            set.addAll(departmentRepository.findAllByNameEnLike("%" + searchWord + "%"));

            //HashSetをArrayListに変換
            list = new ArrayList<>(set);

            //listを部署名(日本語)で降順にソート
            list.sort(
                    (a, b) ->
                    Collator.getInstance().compare(b.getNameJp(), a.getNameJp())
                    );

        } catch (RuntimeException e) {
            //検索に失敗した場合例外にスロー
            throw e;
        }

        return list;
    }

    /** 入力値のvalidationチェック */
    @Override
    public Integer checkStoreInput(Department department) {

        //ステータスコードを格納する変数
        Integer inputStatus = 0;

        //部署名(日本語)のvalidationチェック
        //空欄、空文字列、NULLチェック
        if (department.getNameJp().isBlank()) {

            //部署名(日本語)が空欄、空文字列、NULLだった場合ステータスコードを格納してコントローラーに返す
            inputStatus = Constants.VALIDATION_ERROR_BLANK_JP.getStatusCode();
            return inputStatus;

        //1文字以上255文字以内であることを確認
        } else if (!(department.getNameJp().length() >= 1)
                    || !(department.getNameJp().length() <= 255)) {

            //部署名(日本語)が1文字以上255文字以内でなかった場合ステータスコードを格納してコントローラーに返す
            inputStatus = Constants.VALIDATION_ERROR_BETWEEN_JP.getStatusCode();
            return inputStatus;
        }

        //部署名(英語)のvalidationチェック
        //空欄、空文字列、NULLチェック
        if (department.getNameEn().isBlank()) {

            //部署名(英語)が空欄、空文字列、NULLだった場合ステータスコードを格納してコントローラーに返す
            inputStatus = Constants.VALIDATION_ERROR_BLANK_EN.getStatusCode();
            return inputStatus;

        //1文字以上255文字以内であることを確認
        } else if (!(department.getNameEn().length() >= 1)
                || !(department.getNameEn().length() <= 255)) {

            //部署名(英語)が1文字以上255文字内でなかった場合ステータスコードを格納してコントローラーに返す
            inputStatus = Constants.VALIDATION_ERROR_BETWEEN_EN.getStatusCode();
            return inputStatus;

        //半角英数字であることを確認
        } else if (!(department.getNameEn().matches("[a-zA-Z0-9]+"))) {

            //部署名が半角英数字以外の文字を含んでいた場合ステータスコードを格納してコントローラーに返す
            inputStatus = Constants.VALIDATION_ERROR_FORMAT_EN.getStatusCode();
            return inputStatus;
        }

        //入力値に問題がなかった場合validationチェック成功の一時ステータスコードをコントローラーに返す
        inputStatus = Constants.SUCCESS_INPUT_VALIDATION.getStatusCode();
        return inputStatus;
    }

    /** 部署を追加・変更 */
    @Override
    public Integer storeDepartment(Department department) {

        //ステータスコードを格納する変数
        Integer storeStatus = 0;

        //insertかupdateか確認するフラグ
        Integer processId = 0;

        //入力された部署名が既存か確認するフラグ
        boolean departmentJpExistsFlg = false;
        boolean departmentEnExistsFlg = false;

        //処理種別判定
        if (Optional.ofNullable(department.getId()).isEmpty()) {

            //引数のエンティティにIDがセットしていなかった場合insert
            processId = Constants.INSERT.getStatusCode();
        } else {

            //引数のエンティティにIDがセットしてあった場合update
            processId = Constants.UPDATE.getStatusCode();
        }

        //入力された値が既存か確認
        if (processId == Constants.INSERT.getStatusCode()) {

            //全部署から部署名(日本語)と部署名(英語)で検索し、既存か確認
            departmentJpExistsFlg = departmentRepository.existsByNameJp(department.getNameJp());
            departmentEnExistsFlg = departmentRepository.existsByNameEn(department.getNameEn());
        } else if (processId == Constants.UPDATE.getStatusCode()) {

            //登録済みの自分の部署名(日本語)と部署名(英語)が入力値と同様か確認
            Optional<Department> departmentSelf = 
                    departmentRepository.findById(department.getId());
            //DBから登録済み部署を取得できなかった場合、例外にスロー
            if (departmentSelf.isEmpty()) {
                throw new RuntimeException();
            }
            //部署名(日本語)、部署名(英語)両方が一致していた場合、エラーコードを返す
            if (department.getNameJp().equals(departmentSelf.get().getNameJp())
                    && department.getNameEn().equals(departmentSelf.get().getNameEn())) {
                storeStatus = Constants.EXISTS_NAME_JP.getStatusCode();
                return storeStatus;
            }

            //自分を除く全部署から部署名(日本語)と部署名(英語)で検索し、既存か確認
            //部署を全件取得
            List<Department> departmentList = departmentRepository.findAll();
            //一件ずつ順に一致しているものがあるか検証
            for (Department dep : departmentList) {

                //自分のIDの時は次のループへ進む
                if (department.getId().equals(dep.getId())) {
                    continue;
                }

                //部署名(日本語)の同値検証
                if (department.getNameJp().equals(dep.getNameJp())) {
                    //一致していた場合フラグを立てる
                    departmentJpExistsFlg = true;
                }

                //部署名(英語)の同値検証
                if (department.getNameEn().equals(dep.getNameEn())) {
                    //一致していた場合フラグを立てる
                    departmentEnExistsFlg = true;
                }

                //部署名(日本語)、部署名(英語)いずれかでも一致フラグが立っていた場合ループを抜ける
                if (departmentJpExistsFlg || departmentEnExistsFlg) {
                    break;
                }
            }

        //processIdがinsertでもupdateでもなかった場合例外にスロー
        } else {
            throw new RuntimeException();
        }

        //部署名(日本語)が存在していた場合
        if (departmentJpExistsFlg) {

            //ステータスコード　を返す
            storeStatus = Constants.EXISTS_NAME_JP.getStatusCode();
            return storeStatus;

        //部署名(英語)が存在していた場合
        } else if (departmentEnExistsFlg) {

            //ステータスコード　を返す
            storeStatus = Constants.EXISTS_NAME_EN.getStatusCode();
            return storeStatus;
        }

        //insert or update実行
        Department savedDepartment = departmentRepository.save(department);

        //入力値とinsert or updateされた値の整合性確認
        if (!department.getNameJp().equals(savedDepartment.getNameJp())
                || !department.getNameEn().equals(savedDepartment.getNameEn())) {

            //例外を返し、rollback
            throw new RuntimeException();
        }

        //正常終了
        storeStatus = Constants.SUCCESS_CREATE.getStatusCode();

        //statusが初期値のまま更新されていなかった場合、例外にスロー
        if (storeStatus == 0) {
            throw new RuntimeException();
        }

        //ステータスコードを返す
        return storeStatus;
    }

    /** 部署IDから部署情報を取得 */
    public Department getDepartment(Long departmentId) {

        //IDから部署情報を取得
        Department department;
        Optional<Department> optional = departmentRepository.findById(departmentId);

        //取得できていなければ空のDepartmentを戻す
        department = optional.orElseGet(Department::new);

        //コントローラーに返す
        return department;
    }

    /** 削除処理 */
    public Integer deleteDepartment (Long departmentId) {

        //ステータス格納用変数
        Integer deleteStatus = 0;

        //delete処理
        try {
            departmentRepository.deleteById(departmentId);
            deleteStatus = Constants.SUCCESS_DELETE.getStatusCode();
        } catch (RuntimeException e) {

            deleteStatus = Constants.FAILURE_DELETE.getStatusCode();
        }

        return deleteStatus;
    }

}
