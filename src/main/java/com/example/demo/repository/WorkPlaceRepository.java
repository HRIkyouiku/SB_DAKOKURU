package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.WorkPlace;

@Repository
public interface WorkPlaceRepository extends JpaRepository<WorkPlace, Long> {

    List<WorkPlace> findAll();

    Optional<WorkPlace> findById(Long workPlaceId);
}
