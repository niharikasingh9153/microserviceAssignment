package com.example.studentservice.service;

import com.example.studentservice.dto.StudentDTO;
import com.example.studentservice.entity.Student;
import com.example.studentservice.exception.StudentNotFoundException;
import com.example.studentservice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentDTO addStudent(StudentDTO dto) {
        Student student = Student.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .course(dto.getCourse())
                .build();
        Student saved = studentRepository.save(student);
        return toDTO(saved);
    }

    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));
        return toDTO(student);
    }

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private StudentDTO toDTO(Student student) {
        return StudentDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .age(student.getAge())
                .course(student.getCourse())
                .build();
    }
}