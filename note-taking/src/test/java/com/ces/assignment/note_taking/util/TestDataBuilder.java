package com.ces.assignment.note_taking.util;

import com.ces.assignment.note_taking.dto.note.CreateNoteRequest;
import com.ces.assignment.note_taking.dto.note.UpdateNoteRequest;
import com.ces.assignment.note_taking.entity.Note;
import com.ces.assignment.note_taking.entity.User;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class TestDataBuilder {

    public static User createTestUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        return user;
    }

    public static Note createTestNote(User user) {
        Note note = new Note();
        note.setTitle("Test Note");
        note.setContent("Test Content");
        note.setUser(user);
        return note;
    }

    public static CreateNoteRequest createNoteRequest() {
        CreateNoteRequest request = new CreateNoteRequest();
        request.setTitle("Test Note");
        request.setContent("Test Content");
        return request;
    }

    public static UpdateNoteRequest updateNoteRequest() {
        UpdateNoteRequest request = new UpdateNoteRequest();
        request.setTitle("Updated Note");
        request.setContent("Updated Content");
        return request;
    }

    public static Jwt createTestJwt() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", "test@example.com");
        claims.put("name", "Test User");

        return new Jwt(
            "token",
            Instant.now(),
            Instant.now().plusSeconds(3600),
            Map.of("alg", "none"),
            claims
        );
    }
} 