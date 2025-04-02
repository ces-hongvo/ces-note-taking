package com.ces.assignment.note_taking.controller;

import com.ces.assignment.note_taking.entity.Note;
import com.ces.assignment.note_taking.entity.User;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class NoteController {

    @GetMapping("/note")
    public List<Note> getNotes() {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        Note note0 = new Note();
        note0.setTitle("Note 0");
        note0.setContent("Note 0 Content");

        Note note1 = new Note();
        note1.setTitle("Note 1");
        note1.setContent("Note 1 Content");

        return Arrays.asList(
                note0,
                note1
        );
    }
}
