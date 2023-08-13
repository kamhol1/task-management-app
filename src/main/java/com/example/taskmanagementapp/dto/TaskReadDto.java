package com.example.taskmanagementapp.dto;

import com.example.taskmanagementapp.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class TaskReadDto {
    private int id;
    private String title;
    private StatusEnum status;
    private PriorityEnum priority;
    private LocalDateTime targetTime;
}
