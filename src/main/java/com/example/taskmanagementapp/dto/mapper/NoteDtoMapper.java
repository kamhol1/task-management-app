package com.example.taskmanagementapp.dto.mapper;

import com.example.taskmanagementapp.dto.NoteDto;
import com.example.taskmanagementapp.model.Note;
import com.example.taskmanagementapp.model.Task;

import java.util.List;
import java.util.stream.Collectors;

public class NoteDtoMapper {

    private NoteDtoMapper() {
    }

    public static NoteDto mapToNoteDto(Note note) {
        return new NoteDto(
                note.getId(),
                note.getContent(),
                note.getTask().getId(),
                note.getCreatedOn()
        );
    }

    public static List<NoteDto> mapToNoteDtoList(Task task) {
        return task.getNotes().stream()
                .map(NoteDtoMapper::mapToNoteDto)
                .collect(Collectors.toList());
    }

    public static Note mapToNoteCreate(NoteDto dto) {
        return Note.builder()
                .content(dto.content())
                .task(Task.builder().id(dto.task()).build())
                .build();
    }

    public static Note mapToNoteUpdate(NoteDto dto, Note toUpdate) {
        toUpdate.setContent(dto.content());
        return toUpdate;
    }
}
