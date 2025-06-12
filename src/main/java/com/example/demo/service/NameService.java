package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Name;

public interface NameService {

    public void save(Name name);

    public Name findByUserId(Long userId);

    public List<Long> searchUsers(String keyword);
}
