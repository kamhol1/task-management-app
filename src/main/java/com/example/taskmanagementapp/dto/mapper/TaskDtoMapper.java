package com.example.taskmanagementapp.dto.mapper;

import com.example.taskmanagementapp.dto.TaskDetailsDto;
import com.example.taskmanagementapp.dto.TaskDto;
import com.example.taskmanagementapp.model.Category;
import com.example.taskmanagementapp.model.Task;
import com.example.taskmanagementapp.model.User;

public class TaskDtoMapper {

    private TaskDtoMapper() {
    }

    public static TaskDto mapToTaskDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCategory().getId(),
                task.getStatus(),
                task.getPriority(),
                task.getUser() != null ? task.getUser().getId() : null,
                task.getUser() != null ? task.getUser().getUsername() : null,
                task.getTargetTime());
    }

    public static TaskDetailsDto mapToTaskDetailsDto(Task task) {
        return new TaskDetailsDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCategory().getId(),
                task.getCategory().getName(),
                task.getStatus(),
                task.getPriority(),
                task.getUser() != null ? task.getUser().getId() : null,
                task.getUser() != null ? task.getUser().getUsername() : null,
                task.getTargetTime(),
                task.getCreatedOn(),
                NoteDtoMapper.mapToNoteDtoList(task));
    }

    public static Task mapToTaskCreate(TaskDto taskDto) {
        return Task.builder()
                .title(taskDto.title())
                .description(taskDto.description())
                .category(Category.builder().id(taskDto.category()).build())
                .status(taskDto.status())
                .priority(taskDto.priority())
                .targetTime(taskDto.targetTime())
                .build();
    }

    public static Task mapToTaskUpdate(TaskDto taskDto, Task toUpdate) {
        toUpdate.setTitle(taskDto.title());
        toUpdate.setDescription(taskDto.description());
        toUpdate.setCategory(Category.builder().id(taskDto.category()).build());
        toUpdate.setStatus(taskDto.status());
        toUpdate.setPriority(taskDto.priority());
        toUpdate.setUser(taskDto.user() != null ? User.builder().id(taskDto.user()).build() : null);
        toUpdate.setTargetTime(taskDto.targetTime());

        return toUpdate;
    }
}
