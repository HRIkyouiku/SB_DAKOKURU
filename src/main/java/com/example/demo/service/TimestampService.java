package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Timestamp;

public interface TimestampService {

    public void save(Timestamp timestamp);

    public Page<Timestamp> findAllByUserId(Long userId, Pageable pageable);

    public Timestamp findLatestByUser(Long userId);

}
