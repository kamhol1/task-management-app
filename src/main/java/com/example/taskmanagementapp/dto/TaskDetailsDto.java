package com.example.taskmanagementapp.dto;

import com.example.taskmanagementapp.model.Priority;
import com.example.taskmanagementapp.model.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record TaskDetailsDto(
        Integer id,
        String title,
        String description,
        Integer categoryId,
        String categoryName,
        Status status,
        Priority priority,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime targetTime,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime createdOn,
        List<NoteDto> notes
) {
}
