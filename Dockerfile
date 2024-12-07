# Multi-stage build

# Stage 1: Build the application using Maven
FROM maven:3.9-eclipse-temurin AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Stage 2: Run the built application
FROM eclipse-temurin:21-jre-alpine

WORKDIR app
COPY --from=builder /app/target/*.jar application.jar

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "application.jar"]
