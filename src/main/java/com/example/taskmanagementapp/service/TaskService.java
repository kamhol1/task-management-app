package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.dto.TaskDto;
import com.example.taskmanagementapp.dto.TaskReadDto;
import com.example.taskmanagementapp.dto.TaskReadWithNotesDto;
import com.example.taskmanagementapp.dto.mapper.TaskReadDtoMapper;
import com.example.taskmanagementapp.exception.TaskNotFoundException;
import com.example.taskmanagementapp.model.StatusEnum;
import com.example.taskmanagementapp.model.Task;
import com.example.taskmanagementapp.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.taskmanagementapp.dto.mapper.TaskDtoMapper.mapToTask;
import static com.example.taskmanagementapp.dto.mapper.TaskReadDtoMapper.mapToTaskReadDtoList;
import static com.example.taskmanagementapp.dto.mapper.TaskReadDtoMapper.mapToTaskReadWithNotesDto;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskReadDto> getAllNotCompletedTasks(int pageNumber, int pageSize) {
        return mapToTaskReadDtoList(
                taskRepository.findAllNotCompletedTasks(PageRequest.of(pageNumber, pageSize))
        );
    }

    public Page<TaskReadDto> getAllTasks(int pageNumber, int pageSize) {
        return taskRepository.findAll(PageRequest.of(pageNumber, pageSize)).map(TaskReadDtoMapper::mapToTaskReadDto);

    }

    public TaskReadWithNotesDto getTask(int id) {
        return mapToTaskReadWithNotesDto(taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException(id)
        ));
    }

    @Transactional
    public void createTask(TaskDto taskDto) {
        taskDto.setStatus(StatusEnum.NEW);
        taskRepository.save(mapToTask(taskDto));
    }

    @Transactional
    public void updateTask(int id, TaskDto taskDto) {
        Task toUpdate = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException(id)
        );

        taskRepository.save(mapToTask(taskDto, toUpdate));
    }
}
