package com.studentmanagement.studentmanagement.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDTO {
    private Long id;
    private String courseName;
    private String code;
    private String description;
}
