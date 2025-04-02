package com.ces.assignment.note_taking.controller;

import com.ces.assignment.note_taking.dto.NoteUpdateRequest;
import com.ces.assignment.note_taking.entity.Note;
import com.ces.assignment.note_taking.entity.User;
import com.ces.assignment.note_taking.service.api.CommandNoteService;
import com.ces.assignment.note_taking.service.api.CommandUserService;
import com.ces.assignment.note_taking.service.api.QueryNoteService;
import com.ces.assignment.note_taking.service.api.QueryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class NoteController {

    @Autowired
    CommandNoteService commandNoteService;

    @Autowired
    CommandUserService commandUserService;

    @Autowired
    QueryNoteService queryNoteService;

    @Autowired
    QueryUserService queryUserService;

    @GetMapping("/note")
    public List<Note> getNotes() {
        User user = getUser();
        return queryNoteService.findByUserId(user.getUserId());
    }

    @PostMapping("/note")
    public Note addNote(@RequestBody NoteUpdateRequest noteUpdateRequest) {
        User user = getUser();
        return commandNoteService.addNote(user, noteUpdateRequest.getTitle(), noteUpdateRequest.getContent());
    }

    @PutMapping("/note/{noteId}")
    public Note updateNote(@RequestBody NoteUpdateRequest noteUpdateRequest, @PathVariable long noteId) {
        User user = getUser();
        return commandNoteService.updateNote(user, noteId, noteUpdateRequest.getTitle(), noteUpdateRequest.getContent());
    }

    @DeleteMapping("/note/{noteId}")
    public Note deleteNote(@PathVariable long noteId) {
        User user = getUser();
        return commandNoteService.deleteNote(user, noteId);
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
