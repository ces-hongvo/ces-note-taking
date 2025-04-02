package com.ces.assignment.note_taking.service.api;

import com.ces.assignment.note_taking.entity.Note;

import java.util.List;

public interface QueryNoteService {
    List<Note> findByUserId(long userId);
}
