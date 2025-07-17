# Task Management System

A RESTful API built with **Java Spring Boot** that allows users to register, authenticate, and manage personal tasks (CRUD operations). This project follows industry-standard practices including layered architecture, JWT-based authentication, validation, testing, Docker setup, and Swagger documentation.

---

## Features

### User Management
- Register: `POST /api/auth/register`
- Login (JWT): `POST /api/auth/login`
- Get Profile (secured): `GET /api/users/me`

### Task Management
- Create Task: `POST /api/tasks`
- List Tasks (with pagination & filtering): `GET /api/tasks?page=0&size=5&status=COMPLETED`
- Get Task by ID: `GET /api/tasks/{id}`
- Update Task: `PUT /api/tasks/{id}`
- Delete Task: `DELETE /api/tasks/{id}`

### Security
- Spring Security + JWT
- Passwords hashed using BCrypt
- `/api/auth/**` is public; all other endpoints are secured
---

## Tech Stack

- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Security + JWT
- H2 / MySQL (configurable)
- Docker, Docker Compose
- Swagger (OpenAPI)

---

## Setup Instructions

### Prerequisites
- Java 21+
- Maven
- Docker & Docker Compose

### Run with Maven (Local)
```bash
mvn clean package -DskipTests
java -jar target/Task-Management-System-0.0.1-SNAPSHOT.jar

### 1. Clone the project

git clone
  https://github.com/kmakashre/Task-management-system
