package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employment_statuses")
@Getter
@Setter
public class EmploymentStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contract_start_date", nullable = false)
    private LocalDate contractStartDate;

    @Column(name = "contract_end_date", nullable = false)
    private LocalDate contractEndDate;

    @Column(name = "business_start_time")
    private LocalTime businessStartTime;

    @Column(name = "business_end_time")
    private LocalTime businessEndTime;

    @Column(name = "coretime_start_time")
    private LocalTime coretimeStartTime;

    @Column(name = "coretime_end_time")
    private LocalTime coretimeEndTime;

    @Column(name = "night_shift_start_time")
    private LocalTime nightShiftStartTime;

    @Column(name = "night_shift_end_time")
    private LocalTime nightShiftEndTime;

    private Byte breakMinute;
    private Byte holidayType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private Map<String, Object> legalHoliday;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private Map<String, Object> nonLegalHoliday;

    private Byte workDeadlineDate;
    private Byte initialCalculationDayOfWeek;
    private Byte initialCalculationMonth;
    private Byte initialCalculationDay;
    private Byte overtimeCalculationMonth;
    private Byte overtimeCalculationDay;
    private boolean counted_unfilled_overtime;
    private Byte flexType;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
