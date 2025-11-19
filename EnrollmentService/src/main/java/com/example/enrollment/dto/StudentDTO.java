package com.example.enrollment.dto;

import lombok.Data;

@Data
public class StudentDTO {
    private Long id;
    private String name;
    private int age;
    private String course;
}