package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.dto.NoteDto;
import com.example.taskmanagementapp.dto.UserDto;
import com.example.taskmanagementapp.exception.NoteNotFoundException;
import com.example.taskmanagementapp.exception.UserNotFoundException;
import com.example.taskmanagementapp.model.Note;
import com.example.taskmanagementapp.model.User;
import com.example.taskmanagementapp.repository.NoteRepository;
import com.example.taskmanagementapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.taskmanagementapp.dto.mapper.NoteDtoMapper.*;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public NoteDto getNote(int id) {
        return mapToNoteDto(noteRepository.findById(id).orElseThrow(
                () -> new NoteNotFoundException(id)
        ));
    }

    @Transactional
    public int createNote(NoteDto noteDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto currentUser = (UserDto) authentication.getPrincipal();
        User user = userRepository.findByUsername(currentUser.getUsername()).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        //TODO: Replace record with class
        NoteDto toSave = new NoteDto(noteDto.id(), noteDto.content(), noteDto.task(), user.getId(), user.getUsername(), noteDto.createdOn());

        Note created = noteRepository.save(mapToNoteCreate(toSave));
        return created.getId();
    }

    @Transactional
    public int updateNote(int id, NoteDto noteDto) {
        Note toUpdate = noteRepository.findById(id).orElseThrow(
                () -> new NoteNotFoundException(id)
        );

        noteRepository.save(mapToNoteUpdate(noteDto, toUpdate));
        return id;
    }

    @Transactional
    public int deleteNote(int id) {
        Note toDelete = noteRepository.findById(id).orElseThrow(
                () -> new NoteNotFoundException(id)
        );

        noteRepository.delete(toDelete);
        return id;
    }
}
