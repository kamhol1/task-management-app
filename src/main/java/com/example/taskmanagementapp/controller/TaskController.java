package com.example.taskmanagementapp.controller;

import com.example.taskmanagementapp.dto.TaskDetailsDto;
import com.example.taskmanagementapp.dto.TaskDto;
import com.example.taskmanagementapp.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

    private final TaskService taskService;

    TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/active")
    ResponseEntity<Page<TaskDto>> getAllActiveTasks(@RequestParam(required = false) Integer page,
                                                    @RequestParam(required = false) Integer size,
                                                    @RequestParam(required = false, defaultValue = "id") String sortField,
                                                    @RequestParam(required = false, defaultValue = "desc") String sortOrder,
                                                    @RequestParam(required = false, value = "id") Integer idFilter,
                                                    @RequestParam(required = false, value = "title") String titleFilter,
                                                    @RequestParam(required = false, value = "status") String statusFilter,
                                                    @RequestParam(required = false, value = "priority") String priorityFilter) {
        int pageNumber = page != null && page >= 0 ? page : 0;
        int pageSize = size != null && size >= 0 ? size : 10;

        return ResponseEntity.ok(
                taskService.getAllActiveTasks(idFilter, titleFilter, statusFilter, priorityFilter, pageNumber, pageSize, sortField, sortOrder));
    }

    @GetMapping("")
    ResponseEntity<Page<TaskDto>> getAllTasks(@RequestParam(required = false) Integer page,
                                              @RequestParam(required = false) Integer size,
                                              @RequestParam(required = false, defaultValue = "id") String sortField,
                                              @RequestParam(required = false, defaultValue = "desc") String sortOrder,
                                              @RequestParam(required = false, value = "id") Integer idFilter,
                                              @RequestParam(required = false, value = "title") String titleFilter,
                                              @RequestParam(required = false, value = "status") String statusFilter,
                                              @RequestParam(required = false, value = "priority") String priorityFilter) {
        int pageNumber = page != null && page >= 0 ? page : 0;
        int pageSize = size != null && size >= 0 ? size : 10;

        return ResponseEntity.ok(
                taskService.getAllTasks(idFilter, titleFilter, statusFilter, priorityFilter, pageNumber, pageSize, sortField, sortOrder));
    }

    @GetMapping("/{id}")
    ResponseEntity<TaskDetailsDto> getTask(@PathVariable int id) {
        return ResponseEntity.ok(
                taskService.getTask(id));
    }

    @PostMapping("")
    ResponseEntity<MessageResponse> createTask(@Valid @RequestBody TaskDto taskDto) {
        taskService.createTask(taskDto);
        return new ResponseEntity<>(new MessageResponse("New task created successfully"), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<MessageResponse> updateTask(@PathVariable int id, @Valid @RequestBody TaskDto taskDto) {
        taskService.updateTask(id, taskDto);
        return ResponseEntity.ok(new MessageResponse("Task updated successfully"));
    }
}
