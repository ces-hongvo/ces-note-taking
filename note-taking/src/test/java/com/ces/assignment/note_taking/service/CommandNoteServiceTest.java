package com.ces.assignment.note_taking.service;

import com.ces.assignment.note_taking.entity.Note;
import com.ces.assignment.note_taking.entity.User;
import com.ces.assignment.note_taking.exception.NoteNotFoundException;
import com.ces.assignment.note_taking.exception.UnauthorizedAccessException;
import com.ces.assignment.note_taking.repository.NoteRepository;
import com.ces.assignment.note_taking.service.impl.CommandNoteServiceImpl;
import com.ces.assignment.note_taking.util.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandNoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private CommandNoteServiceImpl commandNoteService;

    private User testUser;
    private Note testNote;

    @BeforeEach
    void setUp() {
        testUser = TestDataBuilder.createTestUser();
        testNote = TestDataBuilder.createTestNote(testUser);
    }

    @Test
    void addNote_Success() {
        when(noteRepository.save(any(Note.class))).thenReturn(testNote);

        Note result = commandNoteService.addNote(testUser, "Test Note", "Test Content");

        assertNotNull(result);
        assertEquals(testNote.getTitle(), result.getTitle());
        assertEquals(testNote.getContent(), result.getContent());
        verify(noteRepository).save(any(Note.class));
    }

    @Test
    void updateNote_Success() {
        when(noteRepository.findById(1L)).thenReturn(Optional.of(testNote));
        when(noteRepository.save(any(Note.class))).thenReturn(testNote);

        Note result = commandNoteService.updateNote(testUser, 1L, "Updated Note", "Updated Content");

        assertNotNull(result);
        assertEquals("Updated Note", result.getTitle());
        assertEquals("Updated Content", result.getContent());
        verify(noteRepository).save(any(Note.class));
    }

    @Test
    void updateNote_NoteNotFound() {
        when(noteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoteNotFoundException.class, () ->
            commandNoteService.updateNote(testUser, 1L, "Updated Note", "Updated Content")
        );
    }

    @Test
    void updateNote_UnauthorizedAccess() {
        User otherUser = TestDataBuilder.createTestUser();
        otherUser.setUserId(2L);

        when(noteRepository.findById(1L)).thenReturn(Optional.of(testNote));

        assertThrows(UnauthorizedAccessException.class, () ->
            commandNoteService.updateNote(otherUser, 1L, "Updated Note", "Updated Content")
        );
    }
} 