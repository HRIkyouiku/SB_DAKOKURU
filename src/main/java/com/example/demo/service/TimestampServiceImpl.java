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
    public Page<Timestamp> findAllByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable) {

        Page<Timestamp> result = timestampRepository.findAllByUserIdOrderByCreatedAtDesc(userId, pageable);

        return result;
    }

    @Override
    public void save(Timestamp timestamp) {
        timestampRepository.save(timestamp);
    }
}
