package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class WorkPlaceForm implements ValidationGroups {

    private Long id;

    @NotBlank(message = "勤務場所名を入力してください。")
    private String name;
}