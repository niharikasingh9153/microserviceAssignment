package com.example.enrollment.dto;

import lombok.Data;
import java.util.List;

@Data
public class EnrollmentDTO {
    private String student;
    private List<String> enrolledCourses;
}