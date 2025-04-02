package com.ces.assignment.note_taking.controller;

import com.ces.assignment.note_taking.dto.note.CreateNoteRequest;
import com.ces.assignment.note_taking.dto.note.NoteResponse;
import com.ces.assignment.note_taking.dto.note.NotesListResponse;
import com.ces.assignment.note_taking.dto.note.UpdateNoteRequest;
import com.ces.assignment.note_taking.entity.Note;
import com.ces.assignment.note_taking.entity.User;
import com.ces.assignment.note_taking.mapper.NoteMapper;
import com.ces.assignment.note_taking.service.api.CommandNoteService;
import com.ces.assignment.note_taking.service.api.CommandUserService;
import com.ces.assignment.note_taking.service.api.QueryNoteService;
import com.ces.assignment.note_taking.service.api.QueryUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoteController {

    @Autowired
    private CommandNoteService commandNoteService;

    @Autowired
    private CommandUserService commandUserService;

    @Autowired
    private QueryNoteService queryNoteService;

    @Autowired
    private QueryUserService queryUserService;

    @Autowired
    private NoteMapper noteMapper;

    @GetMapping("/note")
    public ResponseEntity<NotesListResponse> getNotes() {
        User user = getUser();
        List<Note> notes = queryNoteService.findByUserId(user.getUserId());
        return ResponseEntity.ok(noteMapper.toNotesListResponse(notes));
    }

    @PostMapping("/note")
    public ResponseEntity<NoteResponse> addNote(@Valid @RequestBody CreateNoteRequest request) {
        User user = getUser();
        Note note = commandNoteService.addNote(user, request.getTitle(), request.getContent());
        return ResponseEntity.ok(noteMapper.toNoteResponse(note));
    }

    @PutMapping("/note/{noteId}")
    public ResponseEntity<NoteResponse> updateNote(
            @Valid @RequestBody UpdateNoteRequest request,
            @PathVariable long noteId) {
        User user = getUser();
        Note note = commandNoteService.updateNote(user, noteId, request.getTitle(), request.getContent());
        return ResponseEntity.ok(noteMapper.toNoteResponse(note));
    }

    @DeleteMapping("/note/{noteId}")
    public ResponseEntity<NoteResponse> deleteNote(@PathVariable long noteId) {
        User user = getUser();
        Note note = commandNoteService.deleteNote(user, noteId);
        return ResponseEntity.ok(noteMapper.toNoteResponse(note));
    }

    private User getUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Jwt jwt = (Jwt) securityContext.getAuthentication().getPrincipal();

        String email = jwt.getClaim("email");
        User user = queryUserService.findByEmail(email);

        if (user == null) {
            String name = jwt.getClaim("name");
            user = new User();
            user.setEmail(email);
            user.setName(name);

            user = commandUserService.saveUser(name, email);
        }

        return user;
    }
}
