package com.example.taskmanagementapp.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NoteReadDto {
    private String content;
    private LocalDateTime createdOn;
}
