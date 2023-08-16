package com.example.taskmanagementapp.controller;

import com.example.taskmanagementapp.dto.NoteDto;
import com.example.taskmanagementapp.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "http://localhost:4200")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("")
    ResponseEntity<?> createNote(@RequestBody NoteDto noteDto) {
        noteService.createNote(noteDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
