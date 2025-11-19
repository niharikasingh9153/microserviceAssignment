package com.example.enrollment.controller;

import com.example.enrollment.dto.EnrollmentDTO;
import com.example.enrollment.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/enroll/{studentId}/{courseId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void enrollStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        enrollmentService.enrollStudent(studentId, courseId);
    }

    @GetMapping("/enrollments/{studentId}")
    public EnrollmentDTO getEnrollments(@PathVariable Long studentId) {
        return enrollmentService.getEnrollmentsForStudent(studentId);
    }
}