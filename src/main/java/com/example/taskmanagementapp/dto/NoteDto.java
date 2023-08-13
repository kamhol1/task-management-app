package com.example.taskmanagementapp.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NoteDto {
    private String content;
    private int task;
}
