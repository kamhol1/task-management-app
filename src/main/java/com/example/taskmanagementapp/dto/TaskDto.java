package com.example.taskmanagementapp.dto;

import com.example.taskmanagementapp.model.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TaskDto {
    private String title;
    private String description;
    private int category;
    private StatusEnum status;
    private PriorityEnum priority;
    private LocalDateTime targetTime;
}
