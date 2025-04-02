package com.ces.assignment.note_taking.service.impl;

import com.ces.assignment.note_taking.entity.Note;
import com.ces.assignment.note_taking.repository.NoteRepository;
import com.ces.assignment.note_taking.service.api.QueryNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryNoteServiceImpl implements QueryNoteService {

    @Autowired
    NoteRepository noteRepository;

    @Override
    public List<Note> findByUserId(long userId){
        return noteRepository.findByUserUserId(userId);
    }
}
