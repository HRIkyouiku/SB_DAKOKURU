package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Timestamp;

public interface TimestampRepository extends JpaRepository<Timestamp, Long> {

    Page<Timestamp> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    Optional<Timestamp> findTopByUserIdOrderByDateDescTimeDesc(Long userId);

}
