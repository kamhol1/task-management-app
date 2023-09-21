package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.dto.TaskDetailsDto;
import com.example.taskmanagementapp.dto.TaskDto;
import com.example.taskmanagementapp.exception.TaskNotFoundException;
import com.example.taskmanagementapp.model.Category;
import com.example.taskmanagementapp.model.Task;
import com.example.taskmanagementapp.repository.TaskRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Disabled //TODO: Fix tests
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void getAllActiveTasks_returnsEmptyPage() {
        when(taskRepository.findAll(Mockito.<Specification<Task>>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));

        assertTrue(taskService.getAllActiveTasks(null, "", "", "", "", 0, 5, "id", "desc")
                .toList()
                .isEmpty());
        verify(taskRepository).findAll(Mockito.<Specification<Task>>any(), Mockito.<Pageable>any());
    }

    @Test
    void getAllActiveTasks_returnsOneElementPage() {
        List<Task> list = new ArrayList<>();
        Task task = new Task();
        task.setCategory(mock(Category.class));
        list.add(task);

        when(taskRepository.findAll(Mockito.<Specification<Task>>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(list));

        assertEquals(1, taskService.getAllActiveTasks(null, "", "", "", "", 0, 5, "id", "desc")
                .toList()
                .size());
        verify(taskRepository).findAll(Mockito.<Specification<Task>>any(), Mockito.<Pageable>any());
    }

    @Test
    void getAllTasks_returnsEmptyPage() {
        when(taskRepository.findAll(Mockito.<Specification<Task>>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));

        assertTrue(taskService.getAllTasks(null, "", "", "", "", 0, 5, "id", "desc")
                .toList()
                .isEmpty());
        verify(taskRepository).findAll(Mockito.<Specification<Task>>any(), Mockito.<Pageable>any());
    }

    @Test
    void getAllTasks_returnsOneElementPage() {
        List<Task> list = new ArrayList<>();
        Task task = new Task();
        task.setCategory(mock(Category.class));
        list.add(task);

        when(taskRepository.findAll(Mockito.<Specification<Task>>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(list));

        assertEquals(1, taskService.getAllTasks(null, "", "", "", "", 0, 5, "id", "desc")
                .toList()
                .size());
        verify(taskRepository).findAll(Mockito.<Specification<Task>>any(), Mockito.<Pageable>any());
    }

    @Test
    void getTask_returnsTaskDto() {
        Task task = mock(Task.class);
        Category category = mock(Category.class);

        when(task.getCategory()).thenReturn(category);
        when(category.getId()).thenReturn(1);
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));

        assertInstanceOf(TaskDetailsDto.class, taskService.getTask(1));
        verify(taskRepository).findById(1);
    }

    @Test
    void getTask_throwsTaskNotFoundException() {
        when(taskRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTask(1));
    }

    @Test
    void createTask_returnsTaskDto() {
        Task task = new Task();
        Category category = mock(Category.class);
        task.setTitle("Title");
        task.setCategory(category);
        TaskDto dto = new TaskDto(null, task.getTitle(), task.getDescription(), task.getCategory().getId(), task.getStatus(), task.getPriority(), task.getUser().getId(), task.getUser().getUsername(), task.getTargetTime());

        when(taskRepository.save(any())).thenReturn(task);

        TaskDto created = taskService.createTask(dto);
        assertEquals(dto, created);
        verify(taskRepository).save(any());
    }

    @Test
    void updateTask_returnsTaskDto() {
        Task task = new Task();
        Category category = mock(Category.class);
        task.setTitle("Title");
        task.setCategory(category);
        TaskDto dto = new TaskDto(null, "Updated title", task.getDescription(), task.getCategory().getId(), task.getStatus(), task.getPriority(), task.getUser().getId(), task.getUser().getUsername(), task.getTargetTime());

        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(taskRepository.save(any())).thenReturn(task);

        assertEquals(dto, taskService.updateTask(1, dto));
        verify(taskRepository).save(any());
    }

    @Test
    void updateTask_throwsTaskNotFoundException() {
        TaskDto dto = mock(TaskDto.class);

        when(taskRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(1, dto));
        verify(taskRepository).findById(1);
    }
}