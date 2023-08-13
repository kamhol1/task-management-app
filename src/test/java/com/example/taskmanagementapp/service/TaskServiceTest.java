package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.dto.TaskReadDto;
import com.example.taskmanagementapp.dto.TaskReadWithNotesDto;
import com.example.taskmanagementapp.exception.TaskNotFoundException;
import com.example.taskmanagementapp.model.*;
import com.example.taskmanagementapp.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private Category category;

    @InjectMocks
    private TaskService taskService;

//    @Test
//    void getAllTasks_returnsTaskList() {
//        // Given
//        List<Task> tasks = new ArrayList<>();
//
//        Task task1 = Task.builder()
//                .id(1)
//                .title("Test1")
//                .description("Test1 body")
//                .category(category)
//                .status(StatusEnum.NEW)
//                .priority(PriorityEnum.HIGH)
//                .targetTime(LocalDateTime.now())
//                .createdOn(LocalDateTime.now())
//                .updatedOn(LocalDateTime.now())
//                .build();
//
//        Task task2 = Task.builder()
//                .id(2)
//                .title("Test2")
//                .description("Test2 body")
//                .category(category)
//                .status(StatusEnum.NEW)
//                .priority(PriorityEnum.LOW)
//                .targetTime(LocalDateTime.now())
//                .createdOn(LocalDateTime.now())
//                .updatedOn(LocalDateTime.now())
//                .build();
//
//        tasks.add(task1);
//        tasks.add(task2);
//
//        // When
//        when(taskRepository.findAll(any(PageRequest.class))).thenReturn(tasks);
//        List<TaskReadDto> result = taskService.getAllTasks(0);
//
//        // Then
//        assertThat(result).hasSize(2);
//        verify(taskRepository, times(1)).findAll(any(PageRequest.class));
//    }

    @Test
    void getTask_existingTaskId_returnsTaskReadWithNotesDto() {
        // Given
        int taskId = 1;
        List<Note> notes = new ArrayList<>();

        Task existingTask = Task.builder()
                .category(category)
                .notes(notes)
                .build();

        // When
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        TaskReadWithNotesDto result = taskService.getTask(taskId);

        // Then
        assertNotNull(result);
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void getTask_nonExistingTaskId_throwsTaskNotFoundException() {
        // Given
        int taskId = 1;

        // When
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Then
        assertThrows(TaskNotFoundException.class, () -> taskService.getTask(taskId));
        verify(taskRepository, times(1)).findById(taskId);
    }

}