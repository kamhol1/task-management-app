package com.example.taskmanagementapp.dto;

import com.example.taskmanagementapp.model.Priority;
import com.example.taskmanagementapp.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TaskDto(
        Integer id,
        @NotBlank(message = "Title must not be blank")
        String title,
        String description,
        @NotNull(message = "Category must not be empty")
        Integer category,
        Status status,
        @NotNull(message = "Priority must not be empty")
        Priority priority,
        Integer user,
        String username,
        LocalDateTime targetTime
) {
}
