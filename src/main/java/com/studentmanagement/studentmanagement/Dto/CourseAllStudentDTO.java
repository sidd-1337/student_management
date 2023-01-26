package com.studentmanagement.studentmanagement.Dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class CourseAllStudentDTO {
    private Long courseId;
    private String courseName;
    private String code;
    private String description;
    private Set<StudentDTO> students;

    public CourseAllStudentDTO(Long id, String courseName, String code, String description, Set<StudentDTO> students) {
        this.courseId = id;
        this.courseName = courseName;
        this.code = code;
        this.description = description;
        this.students = students;
    }

}
