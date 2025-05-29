package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;

public interface CustomUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmployeeNo(int employeeNo);
}
