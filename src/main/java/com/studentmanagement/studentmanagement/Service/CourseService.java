package com.studentmanagement.studentmanagement.Service;

import com.studentmanagement.studentmanagement.Dto.CourseAllStudentDTO;
import com.studentmanagement.studentmanagement.Dto.CourseDTO;
import com.studentmanagement.studentmanagement.Dto.StudentDTO;
import com.studentmanagement.studentmanagement.Entity.Course;
import com.studentmanagement.studentmanagement.Entity.Department;
import com.studentmanagement.studentmanagement.Entity.Student;
import com.studentmanagement.studentmanagement.Repository.CourseRepository;
import com.studentmanagement.studentmanagement.Repository.DepartmentRepository;
import com.studentmanagement.studentmanagement.Repository.StudentRepository;
import com.studentmanagement.studentmanagement.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;


    public Course saveCourse(Course course) {
        if (courseRepository.findByCode(course.getCode()).isPresent()) {
            throw new CourseCodeAlreadyExistException();
        }

        return courseRepository.save(course);
    }

    public List<CourseDTO> findAllCourses() {
        return courseRepository.findAll().stream().map(course -> {
            return courseDTO(course);
        }).collect(Collectors.toList());
    }

    public CourseDTO courseFindById(Long id) {
        return courseDTO(courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException()));
    }

    private CourseDTO courseDTO(Course course) {
        return CourseDTO.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .code(course.getCode())
                .description(course.getDescription())
                .build();
    }

    public CourseAllStudentDTO getAllStudentInCourse(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isEmpty()) {
            throw new CourseIdInvalidException();
        }
        Course course = optionalCourse.get();
        return new CourseAllStudentDTO(
                course.getId(),
                course.getCourseName(),
                course.getCode(),
                course.getDescription(),
                course.getStudent().stream()
                        .map(student -> new StudentDTO(student.getId(),
                                student.getEmail(),
                                student.getUsername()))
                        .collect(Collectors.toSet())
        );

    }


    public Set<CourseAllStudentDTO> getAllStudentInAllCourses() {

        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(course -> {
            return new CourseAllStudentDTO(
                    course.getId(),
                    course.getCourseName(),
                    course.getCode(),
                    course.getDescription(),
                    course.getStudent().stream()
                            .map(student -> new StudentDTO(student.getId(),
                                    student.getEmail(),
                                    student.getUsername()))
                            .collect(Collectors.toSet())
            );
        }).filter(Objects::nonNull).collect(Collectors.toSet());
    }


    public void updateCourse(Long id, CourseDTO course) {
        Course courseDetails = courseRepository.findById(id).orElseThrow(() -> new CourseIdInvalidException());
        courseDetails.setCourseName(course.getCourseName());
        courseDetails.setDescription(course.getDescription());
        courseRepository.save(courseDetails);
    }

    public void deleteCourse(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isEmpty()) {
            throw new CourseIdInvalidException();
        }
        Course course = optionalCourse.get();
        Set<Student> students = course.getStudent();
        Set<Department> departments = course.getDepartments();

        //remove assign students
        if (!students.isEmpty()) {
            for (Student student : students) {
                Optional<Student> optionalStudent = studentRepository.findById(student.getId());
                if (optionalStudent.isPresent()) {
                    optionalStudent.get().getCourses().remove(course);
                    studentRepository.save(optionalStudent.get());
                }

            }
        }

        //remove assign departments
        if (!departments.isEmpty()) {
            for (Department department : departments) {
                Optional<Department> optionalDepartment = departmentRepository.findById(department.getId());
                if (optionalDepartment.isPresent()) {
                    optionalDepartment.get().getCourses().remove(course);
                    departmentRepository.save(optionalDepartment.get());
                }

            }
        }
        courseRepository.delete(course);
    }


}
