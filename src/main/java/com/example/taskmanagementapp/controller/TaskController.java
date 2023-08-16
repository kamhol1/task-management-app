package com.example.taskmanagementapp.controller;

import com.example.taskmanagementapp.dto.TaskDto;
import com.example.taskmanagementapp.dto.TaskReadDto;
import com.example.taskmanagementapp.dto.TaskReadWithNotesDto;
import com.example.taskmanagementapp.service.TaskService;
import org.springframework.data.domain.Page;
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
    ResponseEntity<Page<TaskReadDto>> getAllActiveTasks(@RequestParam(required = false) Integer page,
                                        @RequestParam(required = false) Integer size) {
        int pageNumber = page != null && page >= 0 ? page : 0;
        int pageSize = size != null && size >= 0 ? size : 10;
        return ResponseEntity.ok(
                taskService.getAllActiveTasks(pageNumber, pageSize));
    }

    @GetMapping("")
    ResponseEntity<Page<TaskReadDto>> getAllTasks(@RequestParam(required = false) Integer page,
                                  @RequestParam(required = false) Integer size) {
        int pageNumber = page != null && page >= 0 ? page : 0;
        int pageSize = size != null && size >= 0 ? size : 10;
        return ResponseEntity.ok(
                taskService.getAllTasks(pageNumber, pageSize));
    }

    @GetMapping("/{id}")
    ResponseEntity<TaskReadWithNotesDto> getTask(@PathVariable int id) {
        return ResponseEntity.ok(
                taskService.getTask(id));
    }

    @PostMapping("")
    ResponseEntity<MessageResponse> createTask(@RequestBody TaskDto taskDto) {
        taskService.createTask(taskDto);
        return new ResponseEntity<>(new MessageResponse("New task created successfully"), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<MessageResponse> updateTask(@PathVariable int id, @RequestBody TaskDto taskDto) {
        taskService.updateTask(id, taskDto);
        return ResponseEntity.ok(new MessageResponse("Task updated successfully"));
    }
}
