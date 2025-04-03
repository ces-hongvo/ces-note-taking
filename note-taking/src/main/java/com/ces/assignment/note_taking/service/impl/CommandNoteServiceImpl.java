package com.ces.assignment.note_taking.service.impl;

import com.ces.assignment.note_taking.annotation.LogExecutionTime;
import com.ces.assignment.note_taking.entity.Note;
import com.ces.assignment.note_taking.entity.User;
import com.ces.assignment.note_taking.exception.NoteNotFoundException;
import com.ces.assignment.note_taking.exception.UnauthorizedAccessException;
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
    private NoteRepository noteRepository;

    @Override
    @LogExecutionTime
    public Note addNote(User user, String title, String content) {
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setUser(user);

        return noteRepository.save(note);
    }

    @Override
    @LogExecutionTime
    public Note updateNote(User user, long noteId, String title, String content) {
        Note note = noteRepository.findById(noteId)
            .orElseThrow(() -> new NoteNotFoundException(noteId));

        if (!Objects.equals(note.getUser().getUserId(), user.getUserId())) {
            throw new UnauthorizedAccessException(noteId);
        }

        note.setTitle(title);
        note.setContent(content);

        return noteRepository.save(note);
    }

    @Override
    @LogExecutionTime
    public Note deleteNote(User user, long noteId) {
        Note note = noteRepository.findById(noteId)
            .orElseThrow(() -> new NoteNotFoundException(noteId));

        if (!Objects.equals(note.getUser().getUserId(), user.getUserId())) {
            throw new UnauthorizedAccessException(noteId);
        }

        noteRepository.delete(note);
        return note;
    }
}
