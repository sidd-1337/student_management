package com.studentmanagement.studentmanagement.handler;

import com.studentmanagement.studentmanagement.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNameAlreadyExistException.class)
    public ResponseEntity handleUserNameAlreadyExistException(UserNameAlreadyExistException e) {
        return ResponseEntity.unprocessableEntity().body("UserName already exist");
    }

    @ExceptionHandler(CourseCodeAlreadyExistException.class)
    public ResponseEntity handleUserNameAlreadyExistException(CourseCodeAlreadyExistException e) {
        return ResponseEntity.unprocessableEntity().body("Course code already exist");
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handlResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handlUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.unprocessableEntity().body("User not found");
    }

    @ExceptionHandler(CourseIdInvalidException.class)
    public ResponseEntity handlCourseIdInvalidException(CourseIdInvalidException e) {
        return ResponseEntity.unprocessableEntity().body("Course Ids are Invalid");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity handlUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.unprocessableEntity().body("UserName Not found");
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity handlDepartmentNotFoundException(DepartmentNotFoundException e) {
        return ResponseEntity.unprocessableEntity().body("Department Not found");
    }

}
