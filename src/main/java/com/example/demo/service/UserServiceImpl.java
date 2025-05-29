package com.example.demo.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByEmployeeNo(Integer employeeNo) {
        return userRepository.findByEmployeeNo(employeeNo);
    }

    @Override
    public List<Object[]> getDailyTimestamps(List<Long> userIds, LocalDate startDate, LocalDate endDate) {
        if (userIds == null || userIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<Object[]> results = userRepository.findDailyTimestamps(userIds, startDate, endDate);

        return results;
    }
}
