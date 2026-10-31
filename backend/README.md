# Backend — Spring Boot Application

This directory contains the Java/Spring Boot backend for LabDistrib.

## Current Status

✅ **Stage 3 Complete** — Spring Boot application with test endpoints. No database or authentication yet.

## Technology

- Java 25+ (you have Java 26)
- Spring Boot 4.1.1
- Maven (via Maven Wrapper — no global installation needed)
- Embedded Tomcat web server

## How to Run

From this directory (`backend/`):

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Mac/Linux
./mvnw spring-boot:run
```

The application starts on **http://localhost:8080**

## Available Endpoints

| Endpoint | Description |
|----------|-------------|
| `GET /api/test` | Verify the API is running |
| `GET /api/health` | Health check |
| `GET /api/info` | Application information and roadmap status |

## Project Structure

```
backend/
├── pom.xml                              — Maven configuration (dependencies)
├── mvnw.cmd                             — Maven Wrapper (Windows)
├── mvnw                                 — Maven Wrapper (Linux/Mac)
├── src/
│   ├── main/
│   │   ├── java/com/labdistrib/
│   │   │   ├── LabDistribApplication.java    — Main entry point
│   │   │   └── controller/
│   │   │       └── TestController.java       — Test endpoints
│   │   └── resources/
│   │       ├── application.properties        — App configuration
│   │       └── application-local.properties.example  — Local secrets template
│   └── test/
│       └── java/com/labdistrib/
│           └── LabDistribApplicationTests.java
```

## Key Files Explained

| File | What It Does |
|------|-------------|
| `pom.xml` | Lists all libraries (dependencies) the project needs. Maven downloads them. |
| `LabDistribApplication.java` | The main() method that starts the entire web server. |
| `TestController.java` | Receives HTTP requests at `/api/test`, `/api/health`, `/api/info`. |
| `application.properties` | Settings file — server port, logging level, future database config. |

## Stopping the Server

Press `Ctrl+C` in the terminal where the application is running.

## What Comes Next

- **Stage 4**: MySQL + JPA + Hibernate (database connection)
- **Stage 5**: Product, Category, Manufacturer REST APIs
- **Stage 7**: Authentication with Spring Security + JWT
