package com.example.taskmanagementapp.dto;

import com.example.taskmanagementapp.model.PriorityEnum;
import com.example.taskmanagementapp.model.StatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record TaskDetailsDto(
        int id,
        String title,
        String description,
        int categoryId,
        String categoryName,
        StatusEnum status,
        PriorityEnum priority,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime targetTime,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime createdOn,
        List<NoteDto> notes
) {
}
