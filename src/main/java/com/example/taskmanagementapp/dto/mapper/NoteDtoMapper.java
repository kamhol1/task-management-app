package com.example.taskmanagementapp.dto.mapper;

import com.example.taskmanagementapp.dto.NoteDto;
import com.example.taskmanagementapp.model.Note;
import com.example.taskmanagementapp.model.Task;

public class NoteDtoMapper {

    private NoteDtoMapper() {}

    public static Note mapToNote(NoteDto dto) {
        return Note.builder()
                .content(dto.getContent())
                .task(Task.builder().id(dto.getTask()).build())
                .build();
    }
}
