import { useKeycloak } from "@react-keycloak/web";
import { useEffect, useState } from "react";
import "./notes.css";
import NotesContainer from "./NotesContainer";
import { API_BASE_URL } from "./constants";

type Note = {
  noteId: string;
  title: string;
  content: string;
};

type ApiError = {
  title: string;
  status: number;
  detail: string;
};

const Notes = () => {
  const { keycloak } = useKeycloak();
  const [notes, setNotes] = useState<Note[]>([]);
  const [newNote, setNewNote] = useState<Note>({ noteId: "", title: "", content: "" });
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [error, setError] = useState<ApiError | null>(null);

  const handleApiError = (error: ApiError) => {
    setError(error);
    setTimeout(() => {
      setError(null);
    }, 3000); // Clear error after 3 seconds
  };

  useEffect(() => {
    const getData = async () => {
      try {
        if (keycloak && keycloak.authenticated) {
          await keycloak?.updateToken(1);
          const req = await fetch(`${API_BASE_URL}/note`, {
            headers: {
              ["Authorization"]: `Bearer ${keycloak.token}`,
            },
          });
          
          if (!req.ok) {
            const errorData = await req.json();
            handleApiError({
              title: errorData.title || "Failed to fetch notes",
              status: req.status,
              detail: errorData.detail || "An error occurred while fetching notes"
            });
            return;
          }

          const response = await req.json();
          setNotes(response.notes || []);
        }
      } catch (e) {
        handleApiError({
          title: "Network Error",
          status: 0,
          detail: "Failed to connect to the server"
        });
      }
    };
    getData();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (keycloak && keycloak.authenticated) {
        await keycloak?.updateToken(1);
        const response = await fetch(`${API_BASE_URL}/note`, {
          method: "POST",
          headers: {
            ["Authorization"]: `Bearer ${keycloak.token}`,
            "Content-Type": "application/json",
          },
          body: JSON.stringify(newNote),
        });

        if (!response.ok) {
          const errorData = await response.json();
          handleApiError({
            title: errorData.title || "Failed to create note",
            status: response.status,
            detail: errorData.detail || "An error occurred while creating the note"
          });
          return;
        }

        const createdNote = await response.json();
        setNotes([...notes, createdNote]);
        setNewNote({ noteId: "", title: "", content: "" });
        setIsModalOpen(false);
      }
    } catch (e) {
      handleApiError({
        title: "Network Error",
        status: 0,
        detail: "Failed to connect to the server"
      });
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
      {error && (
        <div className="error-alert">
          <h3>{error.title}</h3>
          <p>Status: {error.status}</p>
          <p>{error.detail}</p>
        </div>
      )}
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
                    required
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