# Using the official OpenJDK 17 image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Gradle build file
COPY build/libs/demo-0.0.1-SNAPSHOT.jar app.jar

# Launch the application
ENTRYPOINT ["java", "-jar", "app.jar"]