export interface Note {
  noteId: string;
  title: string;
  content: string;
};

export interface ApiError {
  title: string;
  status: number;
  detail: string;
};

export type NoteContextType = {
    notes: Note[];
    setNotes: (notes: Note[]) => void;
    getNotes: () => Promise<Response>;
    addNote: (note: Note) => Promise<Response>;
    updateNote: (note: Note) => Promise<Response>;
    deleteNote: (note: Note) => Promise<Response>;
}