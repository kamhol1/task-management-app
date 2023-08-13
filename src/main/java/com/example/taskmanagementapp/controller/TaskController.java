package com.example.taskmanagementapp.controller;

import com.example.taskmanagementapp.dto.TaskDto;
import com.example.taskmanagementapp.dto.TaskReadDto;
import com.example.taskmanagementapp.dto.TaskReadWithNotesDto;
import com.example.taskmanagementapp.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

    private final TaskService taskService;

    TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/not-completed")
    List<TaskReadDto> getAllNotCompletedTasks(@RequestParam(required = false) Integer page,
                                              @RequestParam(required = false) Integer size) {
        int pageNumber = page != null && page >= 0 ? page : 0;
        int pageSize = size != null && size >= 0 ? size : 25;
        return taskService.getAllNotCompletedTasks(pageNumber, pageSize);
    }

    @GetMapping("")
    Page<TaskReadDto> getAllTasks(@RequestParam(required = false) Integer page,
                                  @RequestParam(required = false) Integer size) {
        int pageNumber = page != null && page >= 0 ? page : 0;
        int pageSize = size != null && size >= 0 ? size : 25;
        return taskService.getAllTasks(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    TaskReadWithNotesDto getTask(@PathVariable int id) {
        return taskService.getTask(id);
    }

    @PostMapping("")
    void createTask(@RequestBody TaskDto taskDto) {
        taskService.createTask(taskDto);
    }

    @PutMapping("/{id}")
    void updateTask(@PathVariable int id, @RequestBody TaskDto taskDto) {
        taskService.updateTask(id, taskDto);
    }
}
