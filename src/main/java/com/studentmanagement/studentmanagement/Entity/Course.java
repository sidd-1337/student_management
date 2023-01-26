package com.studentmanagement.studentmanagement.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseName;
    @Column(unique = true)
    private String code;
    private String description;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> student = new HashSet<>();

    @ManyToMany(mappedBy = "courses")
    private Set<Department> departments = new HashSet<>();

}
