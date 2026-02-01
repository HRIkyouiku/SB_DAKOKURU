package com.example.demo.service;

import java.util.List;

//必要なパッケージのインポート 2026/01/27
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Timestamp;

public interface TimestampService {

    public List<Timestamp> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    public void save(Timestamp timestamp);
    
    //コントローラから呼び出すメソッド 2026/01/27
    public Page<Timestamp> findPageByUserId(Long userId, Pageable pageable) ;
}
