package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.example.demo.dto.UserDailyViewDTO;
import com.example.demo.entity.User;

public interface UserService {

	public void save(User user);
	public Optional<User> findById(Long userId);
	public void deleteById(Long userId);
	User findByEmail(String email);
	User findByEmployeeNo(Integer employeeNo);
	List<Object[]> getDailyTimestamps(String name, LocalDate firstDay, LocalDate lastDay, String departmentName);
	List<UserDailyViewDTO> getDailyTimestampsView(String name, LocalDate startDate, LocalDate endDate, String departmentName);
}
