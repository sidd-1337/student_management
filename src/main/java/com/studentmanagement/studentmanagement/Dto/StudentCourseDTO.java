package com.studentmanagement.studentmanagement.Dto;

import com.studentmanagement.studentmanagement.Entity.Course;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class StudentCourseDTO {
    private String email;
    private String username;
    private Set<CourseDTO> ids = new HashSet<>();

    public StudentCourseDTO(String email, String username, Set<CourseDTO> ids) {
        this.email = email;
        this.username = username;
        this.ids = ids;
    }
}
