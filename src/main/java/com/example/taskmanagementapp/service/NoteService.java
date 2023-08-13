package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.dto.NoteDto;
import com.example.taskmanagementapp.model.Note;
import com.example.taskmanagementapp.repository.NoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import static com.example.taskmanagementapp.dto.mapper.NoteDtoMapper.mapToNote;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Transactional
    public void createNote(NoteDto noteDto) {
        noteRepository.save(mapToNote(noteDto));
    }
}
