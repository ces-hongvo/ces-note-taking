package com.ces.assignment.note_taking.dto.note;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateNoteRequest {
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title cannot be longer than 255 characters")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    private String content;
} 