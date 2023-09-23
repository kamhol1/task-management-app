package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.dto.NoteDto;
import com.example.taskmanagementapp.exception.AccessDeniedException;
import com.example.taskmanagementapp.exception.NoteNotFoundException;
import com.example.taskmanagementapp.model.Note;
import com.example.taskmanagementapp.model.User;
import com.example.taskmanagementapp.repository.NoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import static com.example.taskmanagementapp.dto.mapper.NoteDtoMapper.*;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserService userService;

    public NoteService(NoteRepository noteRepository, UserService userService) {
        this.noteRepository = noteRepository;
        this.userService = userService;
    }

    public NoteDto getNote(int id) {
        return mapToNoteDto(noteRepository.findById(id).orElseThrow(
                () -> new NoteNotFoundException(id)
        ));
    }

    @Transactional
    public int createNote(NoteDto noteDto) {
        User user = userService.getCurrentUser();

        Note toSave = mapToNoteCreate(noteDto);
        toSave.setUser(user);

        Note created = noteRepository.save(toSave);
        return created.getId();
    }

    @Transactional
    public int updateNote(int id, NoteDto noteDto) {
        User user = userService.getCurrentUser();

        Note toUpdate = noteRepository.findById(id).orElseThrow(
                () -> new NoteNotFoundException(id)
        );

        if (!toUpdate.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not have permission to edit this note");
        }

        noteRepository.save(mapToNoteUpdate(noteDto, toUpdate));
        return id;
    }

    @Transactional
    public int deleteNote(int id) {
        User user = userService.getCurrentUser();

        Note toDelete = noteRepository.findById(id).orElseThrow(
                () -> new NoteNotFoundException(id)
        );

        if (!toDelete.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not have permission to delete this note");
        }

        noteRepository.delete(toDelete);
        return id;
    }
}
