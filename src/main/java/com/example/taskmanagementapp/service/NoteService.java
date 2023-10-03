package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.dto.NoteDto;
import com.example.taskmanagementapp.dto.UserDto;
import com.example.taskmanagementapp.exception.AccessDeniedException;
import com.example.taskmanagementapp.exception.NoteNotFoundException;
import com.example.taskmanagementapp.model.Note;
import com.example.taskmanagementapp.model.User;
import com.example.taskmanagementapp.repository.NoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import static com.example.taskmanagementapp.dto.mapper.NoteDtoMapper.*;
import static com.example.taskmanagementapp.dto.mapper.UserDtoMapper.mapToUser;

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
        User user = mapToUser(userService.getAuthenticatedUser());

        Note toSave = mapToNoteCreate(noteDto);
        toSave.setUser(user);

        Note created = noteRepository.save(toSave);
        return created.getId();
    }

    @Transactional
    public int updateNote(int id, NoteDto noteDto) {
        UserDto user = userService.getAuthenticatedUser();

        Note toUpdate = noteRepository.findById(id).orElseThrow(
                () -> new NoteNotFoundException(id)
        );

        if (!toUpdate.getUser().getId().equals(user.id())) {
            throw new AccessDeniedException("You do not have permission to edit this note");
        }

        noteRepository.save(mapToNoteUpdate(noteDto, toUpdate));
        return id;
    }

    @Transactional
    public int deleteNote(int id) {
        UserDto user = userService.getAuthenticatedUser();

        Note toDelete = noteRepository.findById(id).orElseThrow(
                () -> new NoteNotFoundException(id)
        );

        if (!toDelete.getUser().getId().equals(user.id())) {
            throw new AccessDeniedException("You do not have permission to delete this note");
        }

        noteRepository.delete(toDelete);
        return id;
    }
}
