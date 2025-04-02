package com.ces.assignment.note_taking.dto;

import lombok.Data;

@Data
public class NoteUpdateRequest {
    private long noteId;
    private String title;
    private String content;
}
