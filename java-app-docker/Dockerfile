# Use an official Maven image as the base image
FROM maven:3.8.5-openjdk-17 AS build
# Set the working directory in the container
WORKDIR /app
# Copy the pom.xml and the project files to the container
COPY note-taking/pom.xml .
COPY note-taking/src ./src
# Build the application using Maven
RUN mvn clean package -DskipTests
# Use an official OpenJDK image as the base image
FROM openjdk:17
# Set the working directory in the container
WORKDIR /app
# Copy the built JAR file from the previous stage to the container
COPY --from=build /app/target/note-taking-0.0.1-SNAPSHOT.jar .
# Set the command to run the application
CMD ["java", "-jar", "note-taking-0.0.1-SNAPSHOT.jar"]
