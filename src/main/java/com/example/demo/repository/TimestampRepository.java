package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Timestamp;

public interface TimestampRepository extends JpaRepository<Timestamp, Long> {

    Page<Timestamp> findAllByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

}
