package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.dto.TaskDetailsDto;
import com.example.taskmanagementapp.dto.TaskDto;
import com.example.taskmanagementapp.dto.mapper.TaskDtoMapper;
import com.example.taskmanagementapp.exception.TaskNotFoundException;
import com.example.taskmanagementapp.model.StatusEnum;
import com.example.taskmanagementapp.model.Task;
import com.example.taskmanagementapp.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.example.taskmanagementapp.dto.mapper.TaskDtoMapper.mapToTaskDetailsDto;
import static com.example.taskmanagementapp.dto.mapper.TaskDtoMapper.mapToTaskDto;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<TaskDto> getAllActiveTasks(int pageNumber, int pageSize, String sortField, String sortOrder) {
        Sort sort = Sort.by(sortOrder.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        return taskRepository.findAllActiveTasks(pageRequest).map(TaskDtoMapper::mapToTaskDto);
    }

    public Page<TaskDto> getAllTasks(int pageNumber, int pageSize, String sortField, String sortOrder) {
        Sort sort = Sort.by(sortOrder.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        return taskRepository.findAll(pageRequest).map(TaskDtoMapper::mapToTaskDto);
    }

    public TaskDetailsDto getTask(int id) {
        return mapToTaskDetailsDto(taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException(id)
        ));
    }

    @Transactional
    public TaskDto createTask(TaskDto taskDto) {
        Task toSave = TaskDtoMapper.mapToTaskCreate(taskDto);
        toSave.setStatus(StatusEnum.NEW);

        Task created = taskRepository.save(toSave);
        return mapToTaskDto(created);
    }

    @Transactional
    public TaskDto updateTask(int id, TaskDto taskDto) {
        Task toUpdate = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException(id)
        );

        Task updated = taskRepository.save(TaskDtoMapper.mapToTaskUpdate(taskDto, toUpdate));
        return mapToTaskDto(updated);
    }
}
