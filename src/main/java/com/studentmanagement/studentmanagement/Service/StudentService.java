package com.studentmanagement.studentmanagement.Service;

import com.studentmanagement.studentmanagement.Dto.CourseDTO;
import com.studentmanagement.studentmanagement.Dto.StudentCourseDTO;
import com.studentmanagement.studentmanagement.Entity.Course;
import com.studentmanagement.studentmanagement.Entity.Student;
import com.studentmanagement.studentmanagement.Repository.CourseRepository;
import com.studentmanagement.studentmanagement.Repository.StudentRepository;
import com.studentmanagement.studentmanagement.exception.CourseIdInvalidException;
import com.studentmanagement.studentmanagement.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public Student addCourseToStudent(Long studentId, List<Long> courses) {

        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isEmpty()) {
            throw new UserNotFoundException();
        }

        courses.stream().forEach(id -> {
            Optional<Course> optional = courseRepository.findById(id);
            if (optional.isPresent()) {
                optionalStudent.get().getCourses().add(optional.get());
            } else {
                throw new CourseIdInvalidException();
            }
        });
        return studentRepository.save(optionalStudent.get());
    }

    public StudentCourseDTO getCoursesForStudent(Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty()) {
            throw new UserNotFoundException();
        }

        Student student = optionalStudent.get();
        return new StudentCourseDTO(
                student.getEmail(),
                student.getUsername(),
                student.getCourses().stream()
                        .map(course -> new CourseDTO(course.getId(),
                                course.getCourseName(),
                                course.getCode(), course.getDescription()))
                        .collect(Collectors.toSet())
        );
    }

    public void deleteCourseFromStudent(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new UserNotFoundException());
        Set<Course> courses = student.getCourses();
        boolean validCourseId = false;

        Iterator<Course> iterator = courses.iterator();
        while (iterator.hasNext()) {
            Course element = iterator.next();
            if (element.getId().equals(courseId)) {
                validCourseId = true;
                iterator.remove();
                student.getCourses().remove(element);
                studentRepository.save(student);
            }
        }

        if (!validCourseId) {
            throw new CourseIdInvalidException();
        }
    }

    public void deleteAllCoursesFromStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new UserNotFoundException());
        student.setCourses(Collections.emptySet());
        studentRepository.save(student);
    }


}
