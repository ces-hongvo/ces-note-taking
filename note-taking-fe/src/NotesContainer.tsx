import React, { useState } from 'react';
import { useKeycloak } from "@react-keycloak/web";
import MarkdownEditor from './MarkdownEditor';
import './notes.css';

type Note = {
  noteId: string;
  title: string;
  content: string;
};

interface NotesContainerProps {
  notes: Note[];
  onNoteUpdate: (updatedNote: Note | Note[]) => void;
}

const NotesContainer: React.FC<NotesContainerProps> = ({ notes, onNoteUpdate }) => {
  const { keycloak } = useKeycloak();
  const [selectedNote, setSelectedNote] = useState<Note | null>(null);
  const [isUpdating, setIsUpdating] = useState(false);
  const [isDeleting, setIsDeleting] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');

  const handleUpdate = async () => {
    if (!selectedNote) return;
    
    setIsUpdating(true);
    try {
      if (keycloak && keycloak.authenticated) {
        await keycloak?.updateToken(1);
        const response = await fetch(`http://localhost:8081/note/${selectedNote.noteId}`, {
          method: "PUT",
          headers: {
            ["Authorization"]: `Bearer ${keycloak.token}`,
            "Content-Type": "application/json",
          },
          body: JSON.stringify(selectedNote),
        });
        if (response.ok) {
          const updatedNote = await response.json();
          onNoteUpdate(updatedNote);
        }
      }
    } catch (e) {
      console.log("ERROR", e);
    } finally {
      setIsUpdating(false);
    }
  };

  const handleDelete = async () => {
    if (!selectedNote) return;
    
    setIsDeleting(true);
    try {
      if (keycloak && keycloak.authenticated) {
        await keycloak?.updateToken(1);
        const response = await fetch(`http://localhost:8081/note/${selectedNote.noteId}`, {
          method: 'DELETE',
          headers: {
            ['Authorization']: `Bearer ${keycloak.token}`,
          },
        });
        
        if (response.ok) {
          // Remove the deleted note from the list
          const updatedNotes = notes.filter(note => note.noteId !== selectedNote.noteId);
          onNoteUpdate(updatedNotes);
          setSelectedNote(null);
        }
      }
    } catch (error) {
      console.error('Error deleting note:', error);
    } finally {
      setIsDeleting(false);
    }
  };

  const filteredNotes = notes.filter(note => {
    const searchLower = searchQuery.toLowerCase();
    return (
      note.title.toLowerCase().includes(searchLower) ||
      note.content.toLowerCase().includes(searchLower)
    );
  });

  return (
    <div className="notes-wrapper">
      <div className="search-container">
        <input
          type="text"
          placeholder="Search notes..."
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          className="search-input"
        />
      </div>
      <div className="notes-container">
        <div className="notes-titles">
          {filteredNotes.map((note) => (
            <div
              key={note.noteId}
              className={`note-title ${selectedNote?.noteId === note.noteId ? 'selected' : ''}`}
              onClick={() => setSelectedNote(note)}
            >
              {note.title}
            </div>
          ))}
        </div>
        <div className="note-content">
          {selectedNote ? (
            <>
              <div className="note-content-header">
                <input
                  type="text"
                  value={selectedNote.title}
                  onChange={(e) => setSelectedNote({ ...selectedNote, title: e.target.value })}
                  className="title-input"
                  placeholder="Note Title"
                />
              </div>
              <MarkdownEditor
                content={selectedNote.content}
                onChange={(content) => setSelectedNote({ ...selectedNote, content })}
              />
            </>
          ) : (
            <div className="no-note-selected">
              Select a note to view its content
            </div>
          )}
        </div>
        {selectedNote && (
        <div className="update-button-container">
          <button
            onClick={handleUpdate}
            disabled={isUpdating}
            className="update-button"
          >
            {isUpdating ? 'Updating...' : 'Update Note'}
          </button>
          <button
            onClick={handleDelete}
            disabled={isDeleting}
            className="delete-button"
          >
            {isDeleting ? 'Deleting...' : 'Delete Note'}
          </button>
        </div>
        )}
      </div>
    </div>
  );
};

export default NotesContainer; 