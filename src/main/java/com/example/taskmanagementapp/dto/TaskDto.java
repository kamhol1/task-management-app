package com.example.taskmanagementapp.dto;

import com.example.taskmanagementapp.model.PriorityEnum;
import com.example.taskmanagementapp.model.StatusEnum;
import jakarta.validation.constraints.Min;
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
        StatusEnum status,
        @NotNull(message = "Priority must not be empty")
        PriorityEnum priority,
        LocalDateTime targetTime
) {
}
