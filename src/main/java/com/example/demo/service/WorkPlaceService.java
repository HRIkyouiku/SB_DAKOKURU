package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.WorkPlace;

public interface WorkPlaceService {
    public List<WorkPlace> findAll();

    public void save(WorkPlace workPlace);

    public Optional<WorkPlace> findById(Long workPlaceId);
}
