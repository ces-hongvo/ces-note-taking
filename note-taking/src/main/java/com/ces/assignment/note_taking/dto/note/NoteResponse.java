package com.ces.assignment.note_taking.dto.note;

import com.ces.assignment.note_taking.dto.user.UserDTO;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NoteResponse {
    private long noteId;
    private String title;
    private String content;
    private UserDTO user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 