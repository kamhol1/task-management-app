package com.example.taskmanagementapp.dto;

import java.time.LocalDateTime;

public record NoteDto(
        int id,
        String content,
        int task,
        LocalDateTime createdOn
) {
}