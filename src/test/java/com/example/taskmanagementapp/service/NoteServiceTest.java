package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.dto.NoteDto;
import com.example.taskmanagementapp.exception.NoteNotFoundException;
import com.example.taskmanagementapp.model.Note;
import com.example.taskmanagementapp.model.Task;
import com.example.taskmanagementapp.model.User;
import com.example.taskmanagementapp.repository.NoteRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Disabled //TODO: Fix tests
@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    void getNote_returnsNoteDto() {
        Note note = mock(Note.class);
        Task task = mock(Task.class);

        when(note.getTask()).thenReturn(task);
        when(task.getId()).thenReturn(1);
        when(noteRepository.findById(any())).thenReturn(Optional.of(note));

        assertInstanceOf(NoteDto.class, noteService.getNote(1));
        verify(noteRepository).findById(any());
    }

    @Test
    void getNote_throwsNoteNotFoundException() {
        when(noteRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoteNotFoundException.class, () -> noteService.getNote(1));
        verify(noteRepository).findById(any());
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

        when(noteRepository.save(any())).thenReturn(note);

        assertEquals(1, noteService.createNote(dto));
        verify(noteRepository).save(any());
    }

    @Test
    void updateNote_returnsNoteId() {
        Note note = new Note();
        note.setId(1);
        note.setContent("Content");
        note.setTask(mock(Task.class));

        NoteDto dto = new NoteDto(
                note.getId(),
                note.getContent(),
                note.getTask().getId(),
                note.getUser() != null ? note.getUser().getId() : null,
                note.getUser() != null ? note.getUser().getUsername() : null,
                note.getCreatedOn());

        when(noteRepository.findById(any())).thenReturn(Optional.of(note));
        when(noteRepository.save(any())).thenReturn(note);

        assertEquals(1, noteService.updateNote(1, dto));
        verify(noteRepository).findById(any());
        verify(noteRepository).save(any());
    }

    @Test
    void updateNote_throwsNoteNotFoundException() {
        NoteDto dto = mock(NoteDto.class);

        when(noteRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoteNotFoundException.class, () -> noteService.updateNote(1, dto));
        verify(noteRepository).findById(any());
    }

    @Test
    void deleteNote_returnsNoteId() {
        Note note = new Note();
        note.setId(1);

        when(noteRepository.findById(any())).thenReturn(Optional.of(note));

        int deletedId = noteService.deleteNote(1);
        assertEquals(1, deletedId);
        verify(noteRepository).findById(any());
        verify(noteRepository).delete(any());
    }

    @Test
    void deleteNote_throwsNoteNotFoundException() {
        when(noteRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoteNotFoundException.class, () -> noteService.deleteNote(1));
        verify(noteRepository).findById(any());
    }
}