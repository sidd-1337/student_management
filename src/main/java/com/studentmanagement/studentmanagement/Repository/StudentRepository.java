package com.studentmanagement.studentmanagement.Repository;

import com.studentmanagement.studentmanagement.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByEmail(String email);

    Optional<Student> findByUsername(String userName);

}
