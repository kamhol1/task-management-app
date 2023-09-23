package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.dto.NoteDto;
import com.example.taskmanagementapp.dto.UserAuthDto;
import com.example.taskmanagementapp.exception.NoteNotFoundException;
import com.example.taskmanagementapp.model.Note;
import com.example.taskmanagementapp.model.Task;
import com.example.taskmanagementapp.model.User;
import com.example.taskmanagementapp.repository.NoteRepository;
import com.example.taskmanagementapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @Mock
    private UserService userService;

    @Test
    void getNote_returnsNoteDto() {
        Note note = mock(Note.class);
        Task task = mock(Task.class);
        User user = mock(User.class);

        when(note.getTask()).thenReturn(task);
        when(note.getUser()).thenReturn(user);
        when(task.getId()).thenReturn(1);
        when(noteRepository.findById(1)).thenReturn(Optional.of(note));

        assertInstanceOf(NoteDto.class, noteService.getNote(1));
        verify(noteRepository).findById(1);
    }

    @Test
    void getNote_throwsNoteNotFoundException() {
        when(noteRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoteNotFoundException.class, () -> noteService.getNote(1));
        verify(noteRepository).findById(1);
    }

    @Test
    void createNote_returnsNoteId() {
        Note note = new Note();
        note.setId(1);
        note.setContent("Content");
        note.setTask(mock(Task.class));
        note.setUser(mock(User.class));

        NoteDto dto = new NoteDto(
                note.getId(),
                note.getContent(),
                note.getTask().getId(),
                note.getUser().getId(),
                note.getUser().getUsername(),
                note.getCreatedOn());

        User user = User.builder()
                .username("username")
                .build();

        when(noteRepository.save(any())).thenReturn(note);
        when(userService.getCurrentUser()).thenReturn(user);

        assertEquals(1, noteService.createNote(dto));
        verify(noteRepository).save(any());
    }

    @Test
    void updateNote_returnsNoteId() {
        Note note = new Note();
        note.setId(1);
        note.setContent("Content");
        note.setTask(mock(Task.class));

        User user = User.builder()
                .id(1)
                .username("username")
                .build();

        note.setUser(user);

        NoteDto dto = new NoteDto(
                note.getId(),
                note.getContent(),
                note.getTask().getId(),
                note.getUser().getId(),
                note.getUser().getUsername(),
        note.getCreatedOn());


        when(noteRepository.findById(1)).thenReturn(Optional.of(note));
        when(noteRepository.save(any())).thenReturn(note);
        when(userService.getCurrentUser()).thenReturn(user);

        assertEquals(1, noteService.updateNote(1, dto));
        verify(noteRepository).findById(1);
        verify(noteRepository).save(any());
    }

    @Test
    void updateNote_throwsNoteNotFoundException() {
        NoteDto dto = mock(NoteDto.class);

        when(noteRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoteNotFoundException.class, () -> noteService.updateNote(1, dto));
        verify(noteRepository).findById(1);
    }

    @Test
    void deleteNote_returnsNoteId() {
        Note note = new Note();
        note.setId(1);

        User user = User.builder()
                .id(1)
                .username("username")
                .build();

        note.setUser(user);

        when(noteRepository.findById(1)).thenReturn(Optional.of(note));
        when(userService.getCurrentUser()).thenReturn(user);

        int deletedId = noteService.deleteNote(1);
        assertEquals(1, deletedId);
        verify(noteRepository).findById(1);
        verify(noteRepository).delete(any());
    }

    @Test
    void deleteNote_throwsNoteNotFoundException() {
        when(noteRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoteNotFoundException.class, () -> noteService.deleteNote(1));
        verify(noteRepository).findById(1);
    }
}