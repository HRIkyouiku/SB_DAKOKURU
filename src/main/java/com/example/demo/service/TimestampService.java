package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Timestamp;

public interface TimestampService {

    public List<Timestamp> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    public void save(Timestamp timestamp);
}
