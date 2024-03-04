# Use the official maven/Java 11 image as the base image
FROM maven:3.6.3-openjdk-11 AS builder

# Set the current working directory inside the docker image
WORKDIR /app

# Copy the project source code from the host to the image
COPY . .

# Build the project
RUN mvn clean install

# Use OpenJDK 11 image to run the built jar
FROM openjdk:11-jre-slim

# Copy the built jar from the builder image to this image
COPY --from=builder /app/target/blackjack-service-1.0-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# The command to run the application
CMD ["java", "-jar", "app.jar"]
