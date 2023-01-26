package com.studentmanagement.studentmanagement.exception;

public class UserNameAlreadyExistException extends RuntimeException{
    public UserNameAlreadyExistException(String message) {
        super(message);
    }
}
