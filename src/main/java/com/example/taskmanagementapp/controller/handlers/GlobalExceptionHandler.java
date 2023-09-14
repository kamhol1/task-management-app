package com.example.taskmanagementapp.controller.handlers;

import com.example.taskmanagementapp.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            TaskNotFoundException.class,
            CategoryNotFoundException.class,
            NoteNotFoundException.class,
            UserNotFoundException.class
    })
    public ResponseEntity<Map<String, Object>> handleNotFoundException(RuntimeException e) {
        return buildResponseBodyWithStatus(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            InvalidPasswordException.class,
            UserAlreadyExistsException.class,
            PasswordsDoNotMatchException.class
    })
    public ResponseEntity<Map<String, Object>> handleBadRequestException(RuntimeException e) {
        return buildResponseBodyWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Map<String, Object>> buildResponseBodyWithStatus(Exception e, HttpStatus httpStatus) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", e.getMessage());
        return ResponseEntity.status(httpStatus).body(body);
    }

}
