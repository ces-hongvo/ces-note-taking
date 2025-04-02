import Keycloak from "keycloak-js";

const keycloak = new Keycloak({
  url: "http://localhost:8080/auth/",
  realm: "note-taking",
  clientId: "note-taking-react",
});

export default keycloak;