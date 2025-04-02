package com.ces.assignment.note_taking.repository;

import com.ces.assignment.note_taking.entity.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {

    public List<Note> findByUserUserId(long userId);
}
