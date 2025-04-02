package com.ces.assignment.note_taking.exception;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }

    public UnauthorizedAccessException(long noteId) {
        super("You don't have permission to access note with id: " + noteId);
    }
} 