package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.User;

public interface UserService {

    public void save(User user);

    public Optional<User> findById(Long userId);

    public void deleteById(Long userId);

    User findByEmail(String email);

    User findByEmployeeNo(Long employeeNo);

    
    //　【ユーザーごと勤怠一覧】
    
    // １.名前なし＋部署なし
    // 全ユーザー検索
    List<User> userlistfindall();

    // ２.名前あり＋部署なし
    // ユーザー名のみで検索
    List<User> userlistfindByName(String name);

    // ３.名前なし＋部署あり
    // 部署IDのみで検索
    List<User> userlistfindByDepartmentId(Long departmentId);
    
    // ４.名前あり＋部署あり
    // 名前と部署IDで検索
    List<User> userlistfindByNameAndDepartmentId(String name, Long departmentId);

}
