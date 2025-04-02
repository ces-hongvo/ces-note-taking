package com.ces.assignment.note_taking.exception;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(String message) {
        super(message);
    }

    public NoteNotFoundException(long noteId) {
        super("Note not found with id: " + noteId);
    }
} 