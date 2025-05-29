package com.example.demo.dto;

import lombok.Data;

@Data
public class UserDailyTimestampDTO {
    private Long userId;
    private String fnJp;
    private String lnJp;
    private String departmentName;
    private String dailyTimestamps;
}
