package com.studentmanagement.studentmanagement.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDTO {
    private Long studentId;
    private String email;
    private String username;
}
