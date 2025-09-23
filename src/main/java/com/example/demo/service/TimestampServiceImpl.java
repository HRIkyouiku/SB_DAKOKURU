package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Timestamp;
import com.example.demo.repository.TimestampRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimestampServiceImpl implements TimestampService {

    private final TimestampRepository timestampRepository;

    @Override
    public void save(Timestamp timestamp) {
        timestampRepository.save(timestamp);
    }

    @Override
    public Page<Timestamp> findAllByUserId(Long userId, Pageable pageable) {
        return timestampRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    @Override
    public Timestamp findLatestByUser(Long userId) {
        return timestampRepository.findTopByUserIdOrderByDateDescTimeDesc(userId).orElse(null);
    }
}
