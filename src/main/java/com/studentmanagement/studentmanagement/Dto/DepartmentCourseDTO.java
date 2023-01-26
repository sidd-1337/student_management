package com.studentmanagement.studentmanagement.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class DepartmentCourseDTO {
    private Long departmentId;
    private String deptName;
    private String deptCode;
    private String description;
    private Set<CourseDTO> courses;

    public DepartmentCourseDTO(Long departmentId, String deptName, String deptCode, String description, Set<CourseDTO> courseDTOS) {
        this.departmentId = departmentId;
        this.deptName = deptName;
        this.deptCode = deptCode;
        this.description = description;
        this.courses = courseDTOS;
    }
}
