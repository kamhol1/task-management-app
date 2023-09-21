package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.dto.TaskDetailsDto;
import com.example.taskmanagementapp.dto.TaskDto;
import com.example.taskmanagementapp.dto.mapper.TaskDtoMapper;
import com.example.taskmanagementapp.exception.TaskNotFoundException;
import com.example.taskmanagementapp.model.Status;
import com.example.taskmanagementapp.model.Task;
import com.example.taskmanagementapp.repository.TaskRepository;
import com.example.taskmanagementapp.specification.SearchCriteria;
import com.example.taskmanagementapp.specification.TaskSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.taskmanagementapp.dto.mapper.TaskDtoMapper.mapToTaskDetailsDto;
import static com.example.taskmanagementapp.dto.mapper.TaskDtoMapper.mapToTaskDto;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<TaskDto> getAllActiveTasks(
            Integer idFilter,
            String titleFilter,
            String userFilter,
            String statusFilter,
            String priorityFilter,
            int pageNumber,
            int pageSize,
            String sortField,
            String sortOrder
    ) {
        Sort sort = Sort.by(sortOrder.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        TaskSpecification spec = filter(idFilter, titleFilter, userFilter, statusFilter, priorityFilter, Optional.ofNullable(idFilter).isEmpty() && (statusFilter == null || statusFilter.isEmpty()));

        return taskRepository.findAll(spec, pageRequest).map(TaskDtoMapper::mapToTaskDto);
    }

    public Page<TaskDto> getAllTasks(
            Integer idFilter,
            String titleFilter,
            String userFilter,
            String statusFilter,
            String priorityFilter,
            int pageNumber,
            int pageSize,
            String sortField,
            String sortOrder
    ) {
        Sort sort = Sort.by(sortOrder.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        TaskSpecification spec = filter(idFilter, titleFilter, userFilter, statusFilter, priorityFilter, false);

        return taskRepository.findAll(spec, pageRequest).map(TaskDtoMapper::mapToTaskDto);
    }

    public TaskDetailsDto getTask(int id) {
        return mapToTaskDetailsDto(taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException(id)
        ));
    }

    @Transactional
    public TaskDto createTask(TaskDto taskDto) {
        Task toSave = TaskDtoMapper.mapToTaskCreate(taskDto);
        toSave.setStatus(Status.NEW);

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

    private TaskSpecification filter(Integer idFilter, String titleFilter, String userFilter, String statusFilter, String priorityFilter, boolean excludeCompletedAndCancelled) {
        TaskSpecification spec = new TaskSpecification();

        spec.add(new SearchCriteria("excludeCompletedAndCancelled", excludeCompletedAndCancelled));

        if (idFilter != null) {
            spec.add(new SearchCriteria("id", idFilter));
        }
        if (titleFilter != null) {
            spec.add(new SearchCriteria("title", titleFilter));
        }
        if (userFilter != null) {
            spec.add(new SearchCriteria("user", userFilter));
        }
        if (statusFilter != null) {
            spec.add(new SearchCriteria("status", statusFilter));
        }
        if (priorityFilter != null) {
            spec.add(new SearchCriteria("priority", priorityFilter));
        }

        return spec;
    }
}
