package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.dto.TaskDetailsDto;
import com.example.taskmanagementapp.dto.TaskDto;
import com.example.taskmanagementapp.dto.mapper.TaskDtoMapper;
import com.example.taskmanagementapp.exception.AccessDeniedException;
import com.example.taskmanagementapp.exception.TaskNotFoundException;
import com.example.taskmanagementapp.model.Status;
import com.example.taskmanagementapp.model.Task;
import com.example.taskmanagementapp.model.User;
import com.example.taskmanagementapp.repository.TaskRepository;
import com.example.taskmanagementapp.specification.SearchCriteria;
import com.example.taskmanagementapp.specification.TaskSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.taskmanagementapp.dto.mapper.TaskDtoMapper.*;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
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
        User user = userService.getCurrentUser();

        Task toSave = TaskDtoMapper.mapToTaskCreate(taskDto);
        toSave.setStatus(Status.NEW);
        toSave.setUser(user);

        Task created = taskRepository.save(toSave);
        return mapToTaskDto(created);
    }

    @Transactional
    public TaskDto updateTask(int id, TaskDto taskDto) {
        User user = userService.getCurrentUser();

        Task toUpdate = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException(id)
        );

        if (toUpdate.getUser() != null && !toUpdate.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not have permission to edit this task");
        }

        Task updated = taskRepository.save(mapToTaskUpdate(taskDto, toUpdate));
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
