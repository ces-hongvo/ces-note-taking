import { useEffect } from "react";
import "./App.css";
import keycloak from "./keycloak";
import { ReactKeycloakProvider, useKeycloak } from "@react-keycloak/web";
import Notes from "./Notes";

function App() {
  return (
    <ReactKeycloakProvider authClient={keycloak}>
      <SecuredContent />
    </ReactKeycloakProvider>
  );
}
const SecuredContent = () => {
  const { keycloak } = useKeycloak();
  const isLoggedIn = keycloak.authenticated;
  useEffect(() => {
    if (isLoggedIn === false) keycloak?.login();
  }, [isLoggedIn, keycloak]);
  if (!isLoggedIn) return <div>Not logged in</div>;
  return (
    <div>
      <Notes />
    </div>
  );
};
export default App;