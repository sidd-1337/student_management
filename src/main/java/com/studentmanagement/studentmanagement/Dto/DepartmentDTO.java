package com.studentmanagement.studentmanagement.Dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentDTO {
    private String deptName;
    private String deptCode;
    private String description;
}

