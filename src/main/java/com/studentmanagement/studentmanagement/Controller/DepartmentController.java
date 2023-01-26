package com.studentmanagement.studentmanagement.Controller;

import com.studentmanagement.studentmanagement.Dto.DepartmentDTO;
import com.studentmanagement.studentmanagement.Entity.Department;
import com.studentmanagement.studentmanagement.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping()
    public ResponseEntity addDepartment(@RequestBody DepartmentDTO department) {
        Department department1 = departmentService.addDepartment(department);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(department1.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping()
    public ResponseEntity<Object> getAllDepartments() {
        return ResponseEntity.ok(departmentService.findAllDepartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDepartment(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.departmentFindById(id));
    }

    @PostMapping("/{id}/courses")
    public ResponseEntity<Object> addCoursesToDepartment(@PathVariable Long id, @RequestBody List<Long> courses) {
        Department department1 = departmentService.addCoursesToDepartment(id, courses);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(department1.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateDepartment(@PathVariable Long id, @RequestBody DepartmentDTO department) {
        departmentService.updateDepartment(id, department);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
