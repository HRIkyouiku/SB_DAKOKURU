package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Name;
import com.example.demo.repository.NameRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NameServiceImpl implements NameService {

    private final NameRepository nameRepository;

    @Override
    public void save(Name name) {
        nameRepository.save(name);
    }

    @Override
    public Name findByUserId(Long userId) {
        return nameRepository.findByUserId(userId);
    }

    @Override
    public List<Long> searchUsers(String keyword) {
        String searchKeyword = "%" + keyword + "%";
        return nameRepository.searchByKeyword(searchKeyword);
    }
}
