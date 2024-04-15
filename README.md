# spring-backend-jwt

This project is a backend service built with Spring Boot, implementing JWT (JSON Web Token) for authentication.

## Tech Stack

- **Spring Boot**

  - The main framework used for building the backend service. It simplifies the bootstrapping and development of a new Spring application.

- **Spring Security**

  - A powerful and highly customizable authentication and access-control framework. It's used in this project to secure REST endpoints.

- **JWT (JSON Web Token)**

  - Used for securely transmitting information between parties as a JSON object. In this project, it's used for authentication.

- **Hibernate**

  - A Java framework that simplifies the development of Java application to interact with the database. It's used in this project for ORM (Object Relational Mapping).

- **H2 Database**

  - This is an in-memory database that supports JDBC API. It can be embedded in Java applications or run in the client-server mode. It is lightweight and does not require installation. In this project, it is used for development and testing purposes.

## API Endpoints

This project includes several REST API endpoints for user management and authentication. Here are the main ones:

- **POST /signup**: This endpoint is used for user registration. It accepts a JSON object with `username`, `password`, and `email` fields. After successful registration, it returns a confirmation message.

- **POST /login**: This endpoint is used for user authentication. It accepts a JSON object with `username` and `password` fields. If the authentication is successful, it returns a JWT token which can be used for subsequent requests.

Please note that all sensitive information like passwords are securely hashed before being stored in the database, and all data transmission is done over secure HTTPS connections.

## Unit Testing

- /api/signup
- /api/login
