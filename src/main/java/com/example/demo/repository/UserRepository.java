package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {
            "name"
    })
    Optional<User> findById(Long userId);

    void deleteById(Long userId);

    User findByEmail(String email);

    User findByEmployeeNo(Integer employeeNo);
}
