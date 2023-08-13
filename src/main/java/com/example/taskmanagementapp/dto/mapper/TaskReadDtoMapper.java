package com.example.taskmanagementapp.dto.mapper;

import com.example.taskmanagementapp.dto.NoteReadDto;
import com.example.taskmanagementapp.dto.TaskReadDto;
import com.example.taskmanagementapp.dto.TaskReadWithNotesDto;
import com.example.taskmanagementapp.model.Note;
import com.example.taskmanagementapp.model.Task;

import java.util.List;
import java.util.stream.Collectors;

public class TaskReadDtoMapper {

    private TaskReadDtoMapper() {}

    public static TaskReadWithNotesDto mapToTaskReadWithNotesDto(Task task) {
        return TaskReadWithNotesDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .categoryId(task.getCategory().getId())
                .category(task.getCategory().getName())
                .status(task.getStatus())
                .priority(task.getPriority())
                .targetTime(task.getTargetTime())
                .createdOn(task.getCreatedOn())
                .notes(mapToNoteReadDtoList(task))
                .build();
    }

    public static List<TaskReadDto> mapToTaskReadDtoList(List<Task> tasks) {
        return tasks.stream()
                .map(TaskReadDtoMapper::mapToTaskReadDto)
                .collect(Collectors.toList());
    }

    public static TaskReadDto mapToTaskReadDto(Task task) {
        return TaskReadDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .status(task.getStatus())
                .priority(task.getPriority())
                .targetTime(task.getTargetTime())
                .build();
    }
// TODO: move to NoteDtoMapper
    private static List<NoteReadDto> mapToNoteReadDtoList(Task task) {
        return task.getNotes().stream()
                .map(TaskReadDtoMapper::mapToNoteReadDto)
                .collect(Collectors.toList());
    }

    private static NoteReadDto mapToNoteReadDto(Note note) {
        return NoteReadDto.builder()
                .content(note.getContent())
                .createdOn(note.getCreatedOn())
                .build();
    }
}
