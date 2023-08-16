package com.example.taskmanagementapp.dto.mapper;

import com.example.taskmanagementapp.dto.TaskDto;
import com.example.taskmanagementapp.model.Category;
import com.example.taskmanagementapp.model.Task;

public class TaskDtoMapper {

    private TaskDtoMapper() {}

    public static Task mapToTask(TaskDto taskDto) {
        return Task.builder()
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .category(Category.builder().id(taskDto.getCategory()).build())
                .status(taskDto.getStatus())
                .priority(taskDto.getPriority())
                .targetTime(taskDto.getTargetTime())
                .build();
    }

    public static Task mapToTask(TaskDto taskDto, Task toUpdate) {
        toUpdate.setTitle(taskDto.getTitle());
        toUpdate.setDescription(taskDto.getDescription());
        toUpdate.setCategory(Category.builder().id(taskDto.getCategory()).build());
        toUpdate.setStatus(taskDto.getStatus());
        toUpdate.setPriority(taskDto.getPriority());
        toUpdate.setTargetTime(taskDto.getTargetTime());

        return toUpdate;
    }

    public static TaskDto mapToTaskDto(Task task) {
        return TaskDto.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .category(task.getCategory().getId())
                .status(task.getStatus())
                .priority(task.getPriority())
                .targetTime(task.getTargetTime())
                .build();
    }
}
