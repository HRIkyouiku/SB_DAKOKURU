package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Timestamp;

public interface TimestampService {
    
    Page<Timestamp> findAllByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    public void save(Timestamp timestamp);
}
