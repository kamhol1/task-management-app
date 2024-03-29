package com.example.taskmanagementapp.controller;

import com.example.taskmanagementapp.dto.NoteDto;
import com.example.taskmanagementapp.service.NoteService;
import jakarta.validation.Valid;
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

    @GetMapping("/{id}")
    ResponseEntity<NoteDto> getNote(@PathVariable int id) {
        return ResponseEntity.ok(noteService.getNote(id));
    }

    @PostMapping("")
    ResponseEntity<?> createNote(@Valid @RequestBody NoteDto noteDto) {
        noteService.createNote(noteDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<MessageResponse> updateNote(@PathVariable int id, @Valid @RequestBody NoteDto noteDto) {
        noteService.updateNote(id, noteDto);
        return ResponseEntity.ok(new MessageResponse("Note updated successfully"));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<MessageResponse> deleteNote(@PathVariable int id) {
        noteService.deleteNote(id);
        return ResponseEntity.ok(new MessageResponse("Note deleted successfully"));
    }
}
