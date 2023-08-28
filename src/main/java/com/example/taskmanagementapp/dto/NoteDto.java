package com.example.taskmanagementapp.dto;

import java.time.LocalDateTime;

public record NoteDto(
        Integer id,
        String content,
        Integer task,
        LocalDateTime createdOn
) {
}