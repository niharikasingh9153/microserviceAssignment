package com.example.courseservice.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CourseDTO {
    private Long id;
    private String courseName;
    private String duration;
}