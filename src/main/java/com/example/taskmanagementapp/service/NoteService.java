package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.dto.NoteDto;
import com.example.taskmanagementapp.dto.NoteReadDto;
import com.example.taskmanagementapp.model.Note;
import com.example.taskmanagementapp.repository.NoteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import static com.example.taskmanagementapp.dto.mapper.NoteDtoMapper.mapToNote;
import static com.example.taskmanagementapp.dto.mapper.NoteDtoMapper.mapToNoteReadDto;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public NoteReadDto getNote(int id) {
        return mapToNoteReadDto(noteRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Note not found")
        ));
    }

    @Transactional
    public void createNote(NoteDto noteDto) {
        noteRepository.save(mapToNote(noteDto));
    }

    @Transactional
    public NoteReadDto updateNote(int id, NoteReadDto noteReadDto) {
        Note toUpdate = noteRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Note not found")
        );

        Note updated = noteRepository.save(mapToNote(noteReadDto, toUpdate));
        return mapToNoteReadDto(updated);
    }
}
