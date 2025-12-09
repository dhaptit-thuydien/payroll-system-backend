# Enterprise Payroll Management System Backend

## 🚀 Project Overview
This represents the backend core of a Payroll Management System designed with **Spring Boot 3.5.x** and **Java 25**. The project demonstrates a clean, scalable **Layered Architecture** typical of modern enterprise applications, focusing on robust security, clean code principles, and RESTful standards.

## 🛠 Tech Stack
- **Core:** Java 25, Spring Boot 3.5.6
- **Database:** PostgreSQL, Spring Data JPA (Hibernate)
- **Security:** Spring Security 6, JWT (JSON Web Token), BCrypt
- **Tools:** Maven, IntelliJ IDEA, Postman

## ✨ Key Features

### 🔐 Advanced Security & Authentication
- **Stateless Authentication:** Implemented secure, stateless authentication mechanism using **JWT (JSON Web Token)** with **HS512** algorithm.
- **Role-Based Access Control (RBAC):** Granular authorization handling (Admin vs. User roles) ensuring secure API access.
- **Secure Password Handling:** Integrated **BCryptPasswordEncoder** for robust password hashing.
- **Custom Security Filters:** Implemented `OncePerRequestFilter` to intercept and validate tokens for every request.

### 🏗 Architecture & Design Patterns
- **3-Layer Architecture:** Strict separation of concerns (Controller → Service → Repository) to ensure maintainability and testability.
- **DTO Pattern (Data Transfer Object):** Decoupling internal Database Entities from Public APIs using `LoginRequest`, `SignupRequest`, and `JwtResponse`.
- **Dependency Injection:** Utilization of Spring IoC container for loosely coupled components.

### 📡 RESTful API & Error Handling
- **Standardized REST API:** Adherence to HTTP methods (GET, POST, PUT, DELETE) and status codes (200, 201, 401, 403, 404).
- **Global Exception Handling:** Centralized error handling using `@ControllerAdvice` and custom exceptions (`ResourceNotFoundException`) to provide consistent error responses to clients.

### 💾 Data Management
- **JPA & Hibernate ORM:** Efficient database interaction and object mapping.
- **Database Seeding:** Automated data initialization (`CommandLineRunner`) to setup Admin accounts and sample data upon application startup.
- **Dynamic Queries:** Implementation of JPA Query Methods and JPQL for flexible data retrieval.

## ⚙️ How to Run
1. Clone the repository.
2. Configure PostgreSQL database in `application.properties`.
3. Run `mvn install` to build the project.
4. Start the application via `DithuydienApplication.java`.
