package com.example.demo.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Data;

@Data
public class TimestampForm {

    @NotNull(message = "typeは必須です。")
    @Positive(message = "typeは整数でなければなりません。")
    private Byte type;

    @NotNull(message = "work_placeは必須です。")
    @Positive(message = "work_placeは整数でなければなりません。")
    private Long workPlaceId;

}
