package com.example.taskmanagementapp.dto;

import com.example.taskmanagementapp.model.PriorityEnum;
import com.example.taskmanagementapp.model.StatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record TaskDetailsDto(
        Integer id,
        String title,
        String description,
        Integer categoryId,
        String categoryName,
        StatusEnum status,
        PriorityEnum priority,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime targetTime,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime createdOn,
        List<NoteDto> notes
) {
}
