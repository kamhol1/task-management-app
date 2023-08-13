package com.example.taskmanagementapp.dto;

import com.example.taskmanagementapp.model.PriorityEnum;
import com.example.taskmanagementapp.model.StatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class TaskReadWithNotesDto {
    private int id;
    private String title;
    private String description;
    private int categoryId;
    private String category;
    private StatusEnum status;
    private PriorityEnum priority;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime targetTime;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdOn;
    private List<NoteReadDto> notes;
}
