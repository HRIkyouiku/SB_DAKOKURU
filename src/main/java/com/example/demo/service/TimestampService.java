package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Timestamp;

public interface TimestampService {

	public List<Timestamp> findAllByUserIdOrderByCreatedAtDesc(Long userId);
	public void save(Timestamp timestamp);
	public Page<Timestamp> getTimestamps(Pageable pageable);
}
