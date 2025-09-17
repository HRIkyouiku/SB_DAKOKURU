package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByEmployeeNo(Long employeeNo) {
        return userRepository.findByEmployeeNo(employeeNo);
    }


    // 【ユーザーごと勤怠一覧】
    
    // １.名前なし＋部署なし
    // 全ユーザー検索
    @Override
    public List<User> userlistfindall() {
        return userRepository.findAll();
    }
    
    // ２.名前あり＋部署なし
    // ユーザー名のみで検索
    @Override
    public List<User> userlistfindByName(String name) {
        return userRepository.findByNameLike(name);
    }

    // ３.名前なし＋部署あり
    // 部署IDのみで検索
    @Override
    public List<User> userlistfindByDepartmentId(Long departmentId) {
        return userRepository.findByDepartments_Id(departmentId);
    }
    
    // ４.名前あり＋部署あり
    // 名前と部署IDで検索
    @Override
    public List<User> userlistfindByNameAndDepartmentId(String name, Long departmentId) {
        return userRepository.findByNameAndDepartment(name, departmentId);
    }
}