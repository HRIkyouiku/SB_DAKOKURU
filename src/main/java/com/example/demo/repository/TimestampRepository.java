package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Timestamp;

public interface TimestampRepository extends JpaRepository<Timestamp, Long> {

    List<Timestamp> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    // 勤怠データ取得
    List<Timestamp> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);

}
