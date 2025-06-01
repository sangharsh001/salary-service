# Step 1: Use official Maven image with JDK 17 to build the application
FROM maven:3.9.1-eclipse-temurin-17 AS build

# Set working directory inside the container
WORKDIR /app

# Copy the Maven POM file and download dependencies (in batch mode)
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copy the source code to the container
COPY src ./src

# Package the application without running tests
RUN mvn -B clean package -DskipTests

# Step 2: Use a lightweight JRE image for running the application
FROM eclipse-temurin:17-jre-alpine

# Set working directory for runtime container
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/salary-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the Spring Boot app runs on
EXPOSE 8082

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
