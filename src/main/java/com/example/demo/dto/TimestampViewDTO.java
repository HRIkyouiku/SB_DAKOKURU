package com.example.demo.dto;

import java.time.LocalTime;

import lombok.Data;

@Data
public class TimestampViewDTO {

    /** 出 or 退 */
    private String type;

    /** 打刻時間 */
    private LocalTime time;
}