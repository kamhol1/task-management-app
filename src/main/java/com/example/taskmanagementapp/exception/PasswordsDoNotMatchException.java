package com.example.taskmanagementapp.exception;

public class PasswordsDoNotMatchException extends RuntimeException {
    public PasswordsDoNotMatchException() {
        super("Password does not match password confirmation");
    }
}
