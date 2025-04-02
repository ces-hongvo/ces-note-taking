import { useKeycloak } from "@react-keycloak/web";
import { useEffect, useState } from "react";

type Note = {
  title: string;
  content: string;
};
const Notes = () => {
  const { keycloak } = useKeycloak();
  const [notes, setNotes] = useState<Note[]>([]);
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
  return (
    <>
      <div style={{ marginTop: "20px" }}>
        {notes.map((note) => (
          <div key={note.title} style={{ padding: "10px", marginBottom: "20px" }}>
            <span>
              {note.title}: {note.content}
            </span>
          </div>
        ))}
      </div>
      <button
        type="button"
        className="text-blue-800"
        onClick={() => keycloak.logout()}
      >
        Logout ({keycloak?.tokenParsed?.preferred_username})
      </button>
    </>
  );
};
export default Notes;