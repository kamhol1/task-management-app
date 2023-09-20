package com.example.taskmanagementapp.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record NoteDto(
        Integer id,
        @NotBlank(message = "Note content must not be blank")
        String content,
        Integer task,
        Integer userId,
        String username,
        LocalDateTime createdOn
) {
}