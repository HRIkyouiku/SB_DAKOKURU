package com.example.demo.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class UserDailyViewDTO {

    private Long userId;
    private String name;
    private String departmentName;
    private Map<LocalDate, List<TimestampViewDTO>> dailyMap = new HashMap<>();
}
