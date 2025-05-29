package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.WorkPlace;
import com.example.demo.repository.WorkPlaceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkPlaceServiceImpl implements WorkPlaceService {

    private final WorkPlaceRepository workPlaceRepository;

    @Override
    public List<WorkPlace> findAll() {

        List<WorkPlace> list = workPlaceRepository.findAll();

        return list;
    }

    @Override
    public void save(WorkPlace workPlace) {
        workPlaceRepository.save(workPlace);
    }

    @Override
    public Optional<WorkPlace> findById(Long workPlaceId) {
        return workPlaceRepository.findById(workPlaceId);
    }
}
