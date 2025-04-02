package com.ces.assignment.note_taking.exception;

import com.ces.assignment.note_taking.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoteNotFoundException(
            NoteNotFoundException ex, 
            HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "Not Found",
            ex.getMessage(),
            request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedAccessException(
            UnauthorizedAccessException ex,
            HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.FORBIDDEN.value(),
            "Forbidden",
            ex.getMessage(),
            request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex,
            HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            "An unexpected error occurred",
            request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
} 