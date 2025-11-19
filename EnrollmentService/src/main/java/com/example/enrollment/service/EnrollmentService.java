package com.example.enrollment.service;

import com.example.enrollment.dto.CourseDTO;
import com.example.enrollment.dto.EnrollmentDTO;
import com.example.enrollment.dto.StudentDTO;
import com.example.enrollment.entity.Enrollment;
import com.example.enrollment.exception.BadRequestException;
import com.example.enrollment.exception.NotFoundException;
import com.example.enrollment.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final RestTemplate restTemplate;

    @Value("${student-service.url}")
    private String studentServiceUrl;

    @Value("${course-service.url}")
    private String courseServiceUrl;

    public void enrollStudent(Long studentId, Long courseId) {
        // Check if student exists
        StudentDTO student = restTemplate.getForObject(studentServiceUrl + "/students/" + studentId, StudentDTO.class);
        if (student == null) throw new NotFoundException("Student not found");

        // Check if course exists
        CourseDTO course = restTemplate.getForObject(courseServiceUrl + "/courses/" + courseId, CourseDTO.class);
        if (course == null) throw new NotFoundException("Course not found");

        // Check for duplicate enrollment
        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new BadRequestException("Student already enrolled in this course");
        }

        Enrollment enrollment = Enrollment.builder()
                .studentId(studentId)
                .courseId(courseId)
                .build();
        enrollmentRepository.save(enrollment);
    }

    public EnrollmentDTO getEnrollmentsForStudent(Long studentId) {
        // Check if student exists
        StudentDTO student = restTemplate.getForObject(studentServiceUrl + "/students/" + studentId, StudentDTO.class);
        if (student == null) throw new NotFoundException("Student not found");

        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);

        List<String> courseNames = enrollments.stream()
                .map(e -> {
                    CourseDTO course = restTemplate.getForObject(courseServiceUrl + "/courses/" + e.getCourseId(), CourseDTO.class);
                    return course != null ? course.getCourseName() : "Unknown";
                })
                .collect(Collectors.toList());

        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setStudent(student.getName());
        dto.setEnrolledCourses(courseNames);
        return dto;
    }
}