package com.example.taskmanagementapp.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NoteReadDto {
    private int id;
    private String content;
    private LocalDateTime createdOn;
}
