package com.ces.assignment.note_taking.mapper;

import com.ces.assignment.note_taking.dto.note.CreateNoteRequest;
import com.ces.assignment.note_taking.dto.note.NoteResponse;
import com.ces.assignment.note_taking.dto.note.NotesListResponse;
import com.ces.assignment.note_taking.dto.note.UpdateNoteRequest;
import com.ces.assignment.note_taking.dto.user.UserDTO;
import com.ces.assignment.note_taking.entity.Note;
import com.ces.assignment.note_taking.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NoteMapper {
    
    public NoteResponse toNoteResponse(Note note) {
        if (note == null) {
            return null;
        }

        NoteResponse response = new NoteResponse();
        response.setNoteId(note.getNoteId());
        response.setTitle(note.getTitle());
        response.setContent(note.getContent());
        response.setUser(toUserDTO(note.getUser()));
        // Add createdAt and updatedAt once added to the entity
        return response;
    }

    public NotesListResponse toNotesListResponse(List<Note> notes) {
        NotesListResponse response = new NotesListResponse();
        response.setNotes(notes.stream()
                .map(this::toNoteResponse)
                .collect(Collectors.toList()));
        response.setTotalCount(notes.size());
        return response;
    }

    public UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public Note toNote(CreateNoteRequest request) {
        if (request == null) {
            return null;
        }

        Note note = new Note();
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        return note;
    }

    public void updateNoteFromRequest(Note note, UpdateNoteRequest request) {
        if (request == null || note == null) {
            return;
        }

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
    }
} 