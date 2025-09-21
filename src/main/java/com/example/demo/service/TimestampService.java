package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.entity.Timestamp;

public interface TimestampService {

    public List<Timestamp> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    public void save(Timestamp timestamp);
    
    // 勤怠データ取得
    List<Timestamp> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);

}
