package com.example.demo.service;

import java.util.Optional;

import com.example.demo.entity.User;

public interface UserService {

    public void save(User user);

    public Optional<User> findById(Long userId);

    public void deleteById(Long userId);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmployeeNo(Long employeeNo);
}
