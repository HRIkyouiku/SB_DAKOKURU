package com.example.demo.repository;

import java.util.List;

//必要なパッケージのインポート 2026/01/27
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Timestamp;

public interface TimestampRepository extends JpaRepository<Timestamp, Long> {

    List<Timestamp> findAllByUserIdOrderByCreatedAtDesc(Long userId);
    
    //ページドネーション対応のメソッド 2026/01/27
    Page<Timestamp> findAllByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
