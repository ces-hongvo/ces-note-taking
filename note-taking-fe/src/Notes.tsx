import { useKeycloak } from "@react-keycloak/web";
import { useEffect, useState } from "react";
import "./notes.css";
import NotesContainer from "./NotesContainer";

type Note = {
  noteId: string;
  title: string;
  content: string;
};

const Notes = () => {
  const { keycloak } = useKeycloak();
  const [notes, setNotes] = useState<Note[]>([]);
  const [newNote, setNewNote] = useState<Note>({ noteId: "", title: "", content: "" });
  const [isModalOpen, setIsModalOpen] = useState(false);

  useEffect(() => {
    const getData = async () => {
      try {
        if (keycloak && keycloak.authenticated) {
          await keycloak?.updateToken(1);
          const req = await fetch("http://localhost:8081/note", {
            headers: {
              ["Authorization"]: `Bearer ${keycloak.token}`,
            },
          });
          setNotes(await req.json());
        }
      } catch (e) {
        console.log("ERROR", e);
      }
    };
    getData();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (keycloak && keycloak.authenticated) {
        await keycloak?.updateToken(1);
        const response = await fetch("http://localhost:8081/note", {
          method: "POST",
          headers: {
            ["Authorization"]: `Bearer ${keycloak.token}`,
            "Content-Type": "application/json",
          },
          body: JSON.stringify(newNote),
        });
        if (response.ok) {
          const createdNote = await response.json();
          setNotes([...notes, createdNote]);
          setNewNote({ noteId: "", title: "", content: "" });
          setIsModalOpen(false);
        }
      }
    } catch (e) {
      console.log("ERROR", e);
    }
  };

  const handleNoteUpdate = (updatedNote: Note | Note[]) => {
    if (Array.isArray(updatedNote)) {
      // Handle array update (for delete operation)
      setNotes(updatedNote);
    } else {
      // Handle single note update (for update operation)
      setNotes(notes.map(note => 
        note.noteId === updatedNote.noteId ? updatedNote : note
      ));
    }
  };

  return (
    <div className="container">
      <nav className="navbar">
        <h1 className="navbar-title">Notes App</h1>
        <button
          type="button"
          className="logout-button"
          onClick={() => keycloak.logout()}
        >
          Logout ({keycloak?.tokenParsed?.preferred_username})
        </button>
      </nav>

      <div className="main-content">
        <button
          onClick={() => setIsModalOpen(true)}
          className="add-note-button"
        >
          Add New Note
        </button>

        {isModalOpen && (
          <div className="modal-overlay">
            <div className="modal-content">
              <div className="modal-header">
                <h2 className="modal-title">Create New Note</h2>
                <button
                  onClick={() => setIsModalOpen(false)}
                  className="close-button"
                >
                  ×
                </button>
              </div>
              <form onSubmit={handleSubmit}>
                <div>
                  <input
                    type="text"
                    placeholder="Title"
                    value={newNote.title}
                    onChange={(e) => setNewNote({ ...newNote, title: e.target.value })}
                    className="form-input"
                  />
                </div>
                <div className="modal-buttons">
                  <button
                    type="button"
                    onClick={() => setIsModalOpen(false)}
                    className="cancel-button"
                  >
                    Cancel
                  </button>
                  <button
                    type="submit"
                    className="submit-button"
                  >
                    Add Note
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}

        <NotesContainer notes={notes} onNoteUpdate={handleNoteUpdate} />
      </div>
    </div>
  );
};
export default Notes;