package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {
            "name"
    })
    Optional<User> findById(Long userId);

    void deleteById(Long userId);

    Optional<User> findByEmail(String email);
    
    Optional<User> findByEmployeeNo(Long employeeNo);
}
