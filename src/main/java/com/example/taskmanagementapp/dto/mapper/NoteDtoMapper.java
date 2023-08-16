package com.example.taskmanagementapp.dto.mapper;

import com.example.taskmanagementapp.dto.NoteDto;
import com.example.taskmanagementapp.dto.NoteReadDto;
import com.example.taskmanagementapp.model.Note;
import com.example.taskmanagementapp.model.Task;
import org.aspectj.weaver.ast.Not;

import java.util.List;
import java.util.stream.Collectors;

public class NoteDtoMapper {

    private NoteDtoMapper() {}

    public static Note mapToNote(NoteDto dto) {
        return Note.builder()
                .content(dto.getContent())
                .task(Task.builder().id(dto.getTask()).build())
                .build();
    }

    public static Note mapToNote(NoteReadDto dto) {
        return Note.builder()
                .content(dto.getContent())
                .build();
    }

    public static Note mapToNote(NoteReadDto dto, Note toUpdate) {
        toUpdate.setContent(dto.getContent());
        return toUpdate;
    }

    public static NoteReadDto mapToNoteReadDto(Note note) {
        return NoteReadDto.builder()
                .id(note.getId())
                .content(note.getContent())
                .createdOn(note.getCreatedOn())
                .build();
    }

    public static List<NoteReadDto> mapToNoteReadDtoList(Task task) {
        return task.getNotes().stream()
                .map(NoteDtoMapper::mapToNoteReadDto)
                .collect(Collectors.toList());
    }
}
