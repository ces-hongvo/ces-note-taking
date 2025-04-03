# Setup Local Run
## Start the Docker first

Open folder **note-taking-dockers** and run the command

    docker compose up

## Create Keycloak Realm and Users 

Access the URL http://localhost:8080/auth and login with admin account:

Username: admin

Password: password

Add a new realm and select file: **note-taking-dockers/import.json** to import the realm data

Select the Realm **note-taking**
Then select Users and add a new user. After adding a new user, select the **Credentials** to create password for this user. Make sure to turn the temporary off when you create password.

## Start Back End

Open folder **note-taking** and run the command

    mvn spring-boot:run

## Start Front End

Open folder **note-taking-fe** and run the command

    npx vite

## Accessing the App

Now you can access the application with the URL: http://localhost:5173/

Access the application with the user credentials that you have created and you will be seeing the application
