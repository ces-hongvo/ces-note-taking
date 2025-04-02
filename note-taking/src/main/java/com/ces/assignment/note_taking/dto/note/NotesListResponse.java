package com.ces.assignment.note_taking.dto.note;

import lombok.Data;
import java.util.List;

@Data
public class NotesListResponse {
    private List<NoteResponse> notes;
    private int totalCount;
} 