package com.studentmanagement.studentmanagement.Controller;

import com.studentmanagement.studentmanagement.Entity.Student;
import com.studentmanagement.studentmanagement.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/{id}/courses")
    public ResponseEntity<Object> addCoursesForStudent(@PathVariable Long id, @RequestBody List<Long> courses) {
        Student student = studentService.addCourseToStudent(id, courses);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(student.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<Object> getCoursesForStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getCoursesForStudent(id));
    }

    @DeleteMapping("/{id}/courses/{courseid}")
    public ResponseEntity removeCourseFromStudent(@PathVariable Long id, @PathVariable Long courseid) {
        studentService.deleteCourseFromStudent(id, courseid);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/courses")
    public ResponseEntity deleteAllCoursesFromStudent(@PathVariable Long id) {
        studentService.deleteAllCoursesFromStudent(id);
        return ResponseEntity.noContent().build();
    }
}
