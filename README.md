**Spring Boot 3+ JWT Authentication System**
This project demonstrates a Spring Boot 3+ application that leverages Spring Data JPA, Spring Security, Spring Web, and OAuth2 Resource Server to implement a secure login and registration system. Users can authenticate using HTTP POST requests, and upon successful login, a JWT token is generated and returned. This token can then be used to access authenticated routes based on the user's roles.

Features

User registration and login with email and password.
Role-based access control for authenticated routes.
JWT authentication for stateless, secure API access.
Spring Security configuration for custom `UserDetailsService` and `AuthenticationManager`.

Prerequisites

Java 17 or higher
Maven or Gradle
A relational database

Getting Started

Clone the Repository

bash
git clone "link"
cd repository


Configure the Application

1. Database Configuration: Update `application.properties` or `application.yml` with your database connection details.

2. JWT Secret Key: Set the JWT secret key in `application.properties` or `application.yml`.

Build and Run the Application

 Maven

bash
./mvnw spring-boot:run


Gradle
bash
./gradlew bootRun
Usage

 Register a New User

bash
curl -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d '{"email": "user@example.com", "password": "password"}'


Login and Obtain JWT Token

bash
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d '{"email": "user@example.com", "password": "password"}'

 Access Protected Endpoints

Use the JWT token obtained from the login response in the `Authorization` header as a bearer token.

bash
curl -X GET http://localhost:8080/api/data/user -H "Authorization: Bearer YOUR_JWT_TOKEN"


Security

This application uses Spring Security to secure endpoints. It implements a custom `UserDetailsService` and `AuthenticationManager` for authentication against a database using Spring Data JPA repositories. Upon successful login, a JWT token is generated and returned to the user. This token must be included in the `Authorization` header as a bearer token to access authenticated routes.
