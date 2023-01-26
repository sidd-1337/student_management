package com.studentmanagement.studentmanagement.Service;

import com.studentmanagement.studentmanagement.Dto.CourseDTO;
import com.studentmanagement.studentmanagement.Dto.DepartmentCourseDTO;
import com.studentmanagement.studentmanagement.Dto.DepartmentDTO;
import com.studentmanagement.studentmanagement.Entity.Course;
import com.studentmanagement.studentmanagement.Entity.Department;
import com.studentmanagement.studentmanagement.Repository.CourseRepository;
import com.studentmanagement.studentmanagement.Repository.DepartmentRepository;
import com.studentmanagement.studentmanagement.exception.CourseIdInvalidException;
import com.studentmanagement.studentmanagement.exception.DepartmentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Department addDepartment(DepartmentDTO dto) {
        Department department = Department.builder()
                .deptCode(dto.getDeptCode())
                .deptName(dto.getDeptName())
                .description(dto.getDescription())
                .build();
        return departmentRepository.save(department);
    }

    public List<DepartmentCourseDTO> findAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(department -> {
                    return new DepartmentCourseDTO(
                            department.getId(),
                            department.getDeptName(),
                            department.getDeptCode(),
                            department.getDescription(),
                            department.getCourses().stream()
                                    .map(course -> new CourseDTO(course.getId(),
                                            course.getCourseName(),
                                            course.getCode(), course.getDescription()))
                                    .collect(Collectors.toSet())
                    );
                }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public DepartmentCourseDTO departmentFindById(Long id) {

        Department department = departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException());

        return new DepartmentCourseDTO(
                department.getId(),
                department.getDeptName(),
                department.getDeptCode(),
                department.getDescription(),
                department.getCourses().stream()
                        .map(course -> new CourseDTO(course.getId(),
                                course.getCourseName(),
                                course.getCode(), course.getDescription()))
                        .collect(Collectors.toSet())
        );

    }

    public Department updateDepartment(Long id, DepartmentDTO department) {
        Department departmentDetails = departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException());
        departmentDetails.setDeptName(department.getDeptName());
        departmentDetails.setDescription(department.getDescription());
        return departmentRepository.save(departmentDetails);
    }

    public void deleteDepartment(Long id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isEmpty()) {
            throw new DepartmentNotFoundException();
        }

        Department department = optionalDepartment.get();
        //remove assign courses
        if (!department.getCourses().isEmpty()) {
            for (Course course : department.getCourses()) {
                Optional<Course> optionalCourse = courseRepository.findById(course.getId());
                if (optionalCourse.isPresent()) {
                    optionalCourse.get().getDepartments().remove(department);
                    courseRepository.save(optionalCourse.get());
                }

            }
        }
        departmentRepository.delete(optionalDepartment.get());

    }


    public Department addCoursesToDepartment(Long depId, List<Long> courses) {

        Optional<Department> optionalDepartment = departmentRepository.findById(depId);
        if (optionalDepartment.isEmpty()) {
            throw new DepartmentNotFoundException();
        }

        courses.stream().forEach(id -> {
            Optional<Course> optional = courseRepository.findById(id);
            if (optional.isPresent()) {
                optionalDepartment.get().getCourses().add(optional.get());
            } else {
                throw new CourseIdInvalidException();
            }
        });
        return departmentRepository.save(optionalDepartment.get());
    }


}
