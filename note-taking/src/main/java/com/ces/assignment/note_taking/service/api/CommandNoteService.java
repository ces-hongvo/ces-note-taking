package com.ces.assignment.note_taking.service.api;

import com.ces.assignment.note_taking.entity.Note;
import com.ces.assignment.note_taking.entity.User;
import com.ces.assignment.note_taking.exception.NoteNotFoundException;
import com.ces.assignment.note_taking.exception.UnauthorizedAccessException;

public interface CommandNoteService {
    Note addNote(User user, String title, String content);

    Note updateNote(User user, long noteId, String title, String content) 
        throws NoteNotFoundException, UnauthorizedAccessException;

    Note deleteNote(User user, long noteId) 
        throws NoteNotFoundException, UnauthorizedAccessException;
}
