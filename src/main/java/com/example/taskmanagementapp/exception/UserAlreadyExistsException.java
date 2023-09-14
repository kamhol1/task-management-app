package com.example.taskmanagementapp.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("User with given username already exists");
    }
}
