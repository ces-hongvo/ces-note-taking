package com.ces.assignment.note_taking.service.impl;

import com.ces.assignment.note_taking.entity.Note;
import com.ces.assignment.note_taking.entity.User;
import com.ces.assignment.note_taking.repository.NoteRepository;
import com.ces.assignment.note_taking.service.api.CommandNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
public class CommandNoteServiceImpl implements CommandNoteService {
    @Autowired
    NoteRepository noteRepository;

    @Override
    public Note addNote(User user, String title, String content) {
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setUser(user);

        return noteRepository.save(note);
    }

    @Override
    public Note updateNote(User user, long noteId, String title, String content) throws RuntimeException{
        Note note = noteRepository.findById(noteId).orElse(null);
        if (note == null) {
            return note;
        }
        if (!Objects.equals(note.getUser().getUserId(), user.getUserId())) {
            throw new RuntimeException("Forbidden Action");
        }

        note.setTitle(title);
        note.setContent(content);

        return noteRepository.save(note);
    }

    @Override
    public Note deleteNote(User user, long noteId) throws RuntimeException {
        Note note = noteRepository.findById(noteId).orElse(null);
        if (note == null) {
            return note;
        }
        if (!Objects.equals(note.getUser().getUserId(), user.getUserId())) {
            throw new RuntimeException("Forbidden Action");
        }

        noteRepository.delete(note);
        return note;
    }
}
