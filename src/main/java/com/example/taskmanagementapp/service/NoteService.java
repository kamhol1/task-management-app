package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.dto.NoteDto;
import com.example.taskmanagementapp.exception.NoteNotFoundException;
import com.example.taskmanagementapp.model.Note;
import com.example.taskmanagementapp.repository.NoteRepository;
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
                () -> new NoteNotFoundException(id)
        ));
    }

    @Transactional
    public int createNote(NoteDto noteDto) {
        Note created = noteRepository.save(mapToNoteCreate(noteDto));
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
