import React from "react";
import { Note, NoteContextType } from "../interface/types.note";
import { useKeycloak } from "@react-keycloak/web";
import { API_BASE_URL } from "../constants";

export const NoteContext = React.createContext<NoteContextType | null>(null);

export const NoteProvider: React.FC<{children: React.ReactNode}> = ({children}) => {
    const { keycloak } = useKeycloak();

    const [notes, setNotes] = React.useState<Note[]>([]);

    const getNotes = () => {
        return fetch(`${API_BASE_URL}/note`, {
            headers: {
                ["Authorization"]: `Bearer ${keycloak.token}`,
            },
        });
    };

    const addNote = (note: Note) => {
        return fetch(`${API_BASE_URL}/note`, {
            method: "POST",
            headers: {
              ["Authorization"]: `Bearer ${keycloak.token}`,
              "Content-Type": "application/json",
            },
            body: JSON.stringify(note),
        });
    };

    const updateNote = (note: Note) => {
        return fetch(`${API_BASE_URL}/note/${note.noteId}`, {
            method: "PUT",
            headers: {
              ["Authorization"]: `Bearer ${keycloak.token}`,
              "Content-Type": "application/json",
            },
            body: JSON.stringify(note),
        });
    }

    const deleteNote = (note: Note) => {
        return fetch(`${API_BASE_URL}/note/${note.noteId}`, {
            method: 'DELETE',
            headers: {
            ['Authorization']: `Bearer ${keycloak.token}`,
            },
        });
    }

    return (<NoteContext.Provider value={{notes, setNotes, getNotes, addNote, updateNote, deleteNote}}>
            {children}
        </NoteContext.Provider>);
}