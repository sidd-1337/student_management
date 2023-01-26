package com.studentmanagement.studentmanagement.Controller;

import com.studentmanagement.studentmanagement.Dto.CourseDTO;
import com.studentmanagement.studentmanagement.Entity.Course;
import com.studentmanagement.studentmanagement.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping()
    public ResponseEntity<Object> saveCourse(@RequestBody Course course) {
        Course course1 = courseService.saveCourse(course);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(course1.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping()
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.findAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.courseFindById(id));
    }

    //get all students in one subject
    @GetMapping("/{id}/students")
    public ResponseEntity<Object> getAllStudentInCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getAllStudentInCourse(id));
    }

    //get all students under all subjects
    @GetMapping("/students")
    public ResponseEntity<Object> getAllStudentInAllCourses() {
        return ResponseEntity.ok(courseService.getAllStudentInAllCourses());
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCourse(@PathVariable Long id, @RequestBody CourseDTO course) {
        courseService.updateCourse(id, course);
        return ResponseEntity.noContent().build();
    }

    //delete a course
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
