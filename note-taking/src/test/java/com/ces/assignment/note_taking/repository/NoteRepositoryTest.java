package com.ces.assignment.note_taking.repository;

import com.ces.assignment.note_taking.entity.Note;
import com.ces.assignment.note_taking.entity.User;
import com.ces.assignment.note_taking.util.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class NoteRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private NoteRepository noteRepository;

    private User testUser;
    private Note testNote;

    @BeforeEach
    void setUp() {
        entityManager.clear();
        testUser = TestDataBuilder.createTestUser();
        testUser = entityManager.persist(testUser);
        
        testNote = TestDataBuilder.createTestNote(testUser);
        testNote = entityManager.persist(testNote);
        
        entityManager.flush();
    }

    @Test
    void findByUserUserId_Success() {
        List<Note> notes = noteRepository.findByUserUserId(testUser.getUserId());

        assertFalse(notes.isEmpty());
        assertEquals(1, notes.size());
        assertEquals(testNote.getTitle(), notes.get(0).getTitle());
        assertEquals(testNote.getContent(), notes.get(0).getContent());
    }

    @Test
    void findByUserUserId_NoNotes() {
        List<Note> notes = noteRepository.findByUserUserId(testUser.getUserId() + 100);
        assertTrue(notes.isEmpty());
    }
} 