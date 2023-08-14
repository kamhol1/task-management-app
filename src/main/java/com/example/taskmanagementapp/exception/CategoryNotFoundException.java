package com.example.taskmanagementapp.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(int id) {
        super("Category with id " + id + " not found");
    }
}
