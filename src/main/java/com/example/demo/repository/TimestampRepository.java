package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Timestamp;

public interface TimestampRepository extends JpaRepository<Timestamp, Long> {

    List<Timestamp> findAllByUserIdOrderByCreatedAtDesc(Long userId);

}
