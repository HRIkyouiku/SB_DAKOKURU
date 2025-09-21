package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Timestamp;
import com.example.demo.repository.TimestampRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimestampServiceImpl implements TimestampService {

    private final TimestampRepository timestampRepository;

    @Override
    public List<Timestamp> findAllByUserIdOrderByCreatedAtDesc(Long userId) {

        List<Timestamp> result = timestampRepository.findAllByUserIdOrderByCreatedAtDesc(userId);

        return result;
    }

    @Override
    public void save(Timestamp timestamp) {
        timestampRepository.save(timestamp);
    }
    
    // 勤怠データ取得
    @Override
    public List<Timestamp> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end) {
        return timestampRepository.findByUserIdAndDateBetween(userId, start, end);
    }


}
