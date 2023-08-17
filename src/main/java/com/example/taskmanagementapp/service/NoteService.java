package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.dto.NoteDto;
import com.example.taskmanagementapp.model.Note;
import com.example.taskmanagementapp.repository.NoteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import static com.example.taskmanagementapp.dto.mapper.NoteDtoMapper.*;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public NoteDto getNote(int id) {
        return mapToNoteDto(noteRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Note not found")
        ));
    }

    @Transactional
    public void createNote(NoteDto noteDto) {
        noteRepository.save(mapToNoteCreate(noteDto));
    }

    @Transactional
    public NoteDto updateNote(int id, NoteDto noteDto) {
        Note toUpdate = noteRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Note not found")
        );

        Note updated = noteRepository.save(mapToNoteUpdate(noteDto, toUpdate));
        return mapToNoteDto(updated);
    }
}
