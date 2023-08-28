package com.example.taskmanagementapp.dto;

import com.example.taskmanagementapp.model.PriorityEnum;
import com.example.taskmanagementapp.model.StatusEnum;

import java.time.LocalDateTime;

public record TaskDto(
        Integer id,
        String title,
        String description,
        Integer category,
        StatusEnum status,
        PriorityEnum priority,
        LocalDateTime targetTime
) {
}
