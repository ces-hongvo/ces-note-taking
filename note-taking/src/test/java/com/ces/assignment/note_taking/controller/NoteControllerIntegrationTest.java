package com.ces.assignment.note_taking.controller;

import com.ces.assignment.note_taking.config.TestConfig;
import com.ces.assignment.note_taking.dto.note.CreateNoteRequest;
import com.ces.assignment.note_taking.dto.note.UpdateNoteRequest;
import com.ces.assignment.note_taking.entity.Note;
import com.ces.assignment.note_taking.entity.User;
import com.ces.assignment.note_taking.service.api.CommandNoteService;
import com.ces.assignment.note_taking.service.api.QueryNoteService;
import com.ces.assignment.note_taking.service.api.QueryUserService;
import com.ces.assignment.note_taking.util.TestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class)
class NoteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CommandNoteService commandNoteService;

    @MockitoBean
    private QueryNoteService queryNoteService;

    @MockitoBean
    private QueryUserService queryUserService;

    private User testUser;
    private Note testNote;

    @BeforeEach
    void setUp() {
        testUser = TestDataBuilder.createTestUser();
        testNote = TestDataBuilder.createTestNote(testUser);
        when(queryUserService.findByEmail("test@example.com")).thenReturn(testUser);
    }

    @Test
    void getNotes_Success() throws Exception {
        when(queryNoteService.findByUserId(testUser.getUserId()))
            .thenReturn(Collections.singletonList(testNote));

        mockMvc.perform(get("/note")
                .with(jwt().jwt(TestDataBuilder.createTestJwt())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notes[0].title").value(testNote.getTitle()))
                .andExpect(jsonPath("$.notes[0].content").value(testNote.getContent()));
    }

    @Test
    void addNote_Success() throws Exception {
        CreateNoteRequest request = TestDataBuilder.createNoteRequest();
        when(commandNoteService.addNote(eq(testUser), any(), any())).thenReturn(testNote);

        mockMvc.perform(post("/note")
                .with(jwt().jwt(TestDataBuilder.createTestJwt()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(testNote.getTitle()))
                .andExpect(jsonPath("$.content").value(testNote.getContent()));
    }

    @Test
    void updateNote_Success() throws Exception {
        UpdateNoteRequest request = TestDataBuilder.updateNoteRequest();
        when(commandNoteService.updateNote(eq(testUser), eq(1L), any(), any()))
            .thenReturn(testNote);

        mockMvc.perform(put("/note/1")
                .with(jwt().jwt(TestDataBuilder.createTestJwt()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
} 