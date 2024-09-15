## Java Spring Boot Project

- [Description](#description)
- [Features](#features)
- [Technologies](#technologies)
- [Start](#start)
- [Schema](#schema)
- [Endpoints](#endpoints)

## Description

It This project is a Java-based Spring Boot application with a PostgreSQL database and JWT authentication. It includes endpoints for user management and to-do list management. The application is containerized using Docker and orchestrated with Docker Compose.

## Features

- **User Management:** Register, login, update, and delete users.

- **Register, login, update, and delete users.:** Create, retrieve, update, and delete to-do items.

- **JWT Authentication:** Secure endpoints with JWT tokens.

## Technologies 

- **Java:** Programming language used for the application.

- **Spring Boot:** Framework for building the application.

- **PostgreSQL:** Database for storing user and to-do data.

- **Spring Boot:** Framework for building the application.

- **Docker & Docker Compose:** JSON Web Tokens for authentication.

## Start

1. **Clone the Repository**

```bash
git clone https://github.com/andrey-lawyer/java-spring-todos
```
```bash
cd java-spring-todos
```

2. **Start Docker Containers:** Run docker-compose up to start containers with databases and other services.

```bash
docker-compose up -d
```
The main application service, running on port 8081

## Schema

The database schema includes tables for User and Post. Relationships between these tables allow you to organise posts between different users.

## Endpoints

1. **User Endpoints**

- **POST /users/register:** Register a new user.

- **POST /users/login:** Authenticate a user and obtain a JWT token.

- **GET /users:** Retrieve all users (requires authentication).

- **GET /users/{id}:** Retrieve a user by ID (requires authentication).

- **PUT /users/{id}:** Update user details (requires authentication).

- **DELETE /users/{id}:** Delete a user by ID (requires authentication).

2. **To-do Endpoints**

- **POST  /todos:** Create a new to-do item (requires authentication).

- **GET /todos:** Retrieve all to-do items for the logged-in user (requires authentication).

- **GET /todos/{id}:** Retrieve a to-do item by ID (requires authentication)

- **PUT /todos/{id}:** Update a to-do item (requires authentication).

- **DELETE /todos/{id}:** Delete a to-do item by ID (requires authentication).

- **PUT /todos/{id}/complete:** Mark a to-do item as complete (requires authentication).



