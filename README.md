# Product Management REST API

A secure and scalable RESTful Product Management API developed using Java 17 and Spring Boot.

The application provides complete Product CRUD operations with JWT authentication, refresh token rotation, role-based authorization, pagination, input validation, global exception handling, Swagger/OpenAPI documentation, automated testing, HTTPS, database indexing, and Docker support.

## Technology Stack

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Spring Security
- JWT
- Refresh Token
- Jakarta Validation
- JUnit 5
- Mockito
- Spring Boot Test
- H2 Database
- Swagger / OpenAPI
- Docker
- Docker Compose

---

## Features

- Product CRUD operations
- RESTful API design
- API versioning using `/api/v1/`
- Pagination support
- JWT-based authentication
- Refresh token rotation
- Role-based authorization
- Input validation using Jakarta Validation
- Global exception handling
- Database indexing
- HTTPS support
- CORS configuration
- Unit testing with JUnit 5 and Mockito
- Integration testing with Spring Boot Test
- H2 in-memory database for testing
- Swagger/OpenAPI documentation
- Docker and Docker Compose support

---

## API Endpoints

### Authentication APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/auth/login` | Authenticate user and generate access and refresh tokens |
| POST | `/api/v1/auth/refresh` | Generate new access and refresh tokens |

### Product APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/products` | Get paginated list of products |
| GET | `/api/v1/products/{id}` | Get product by ID |
| POST | `/api/v1/products` | Create a new product |
| PUT | `/api/v1/products/{id}` | Update an existing product |
| DELETE | `/api/v1/products/{id}` | Delete a product |
| GET | `/api/v1/products/{id}/items` | Get items associated with a product |

---

## Architecture

The application follows a layered architecture with clear separation of responsibilities.

```text
                    Client
                      |
                      v
              Controller Layer
                      |
                      v
                Service Layer
                      |
                      v
              Repository Layer
                      |
                      v
                Entity Layer
                      |
                      v
                  MySQL
Controller Layer

Handles HTTP requests, request validation, and HTTP responses.

Service Layer

Contains the application's business logic and coordinates application operations.

Repository Layer

Provides database access using Spring Data JPA.

Entity Layer

Represents the persistent database entities.

DTO Layer

Provides separate request and response models for API communication.

Security Layer

Handles authentication, authorization, JWT processing, and refresh token management.

Authentication & Authorization

The application uses Spring Security with JWT-based authentication.

Authentication Flow
Login Request
      |
      v
Authentication Manager
      |
      v
User Authentication
      |
      +----------------------+
      |                      |
      v                      v
Access Token           Refresh Token
      |                      |
      |                      v
      |                Stored in DB
      |
      v
Protected APIs

The access token is used to access protected APIs.

When the access token expires, the refresh token can be used to generate a new access token.

Refresh token rotation is implemented by revoking the old refresh token and generating a new refresh token.

Role-Based Authorization

The application supports role-based authorization using Spring Security.

Protected endpoints can be accessed based on the authenticated user's role.

Example roles:

ADMIN
USER
Database

MySQL is used as the primary relational database.

Main Tables
users
refresh_token
product
item
Product
id
product_name
created_by
created_on
modified_by
modified_on
Item
id
product_id
quantity

The item.product_id column references the product.id column.

Database indexes are used on frequently queried columns to improve lookup performance.

Pagination

Collection endpoints support pagination to efficiently handle large datasets.

Example:

GET /api/v1/products?page=0&size=10

The response includes pagination metadata such as:

Total pages
Total elements
Page size
Current page number
Number of elements
First/last page information
Validation

The application uses Jakarta Validation to validate incoming request data.

Examples include:

Required fields
Valid field values
Request data constraints

Invalid requests are handled through centralized exception handling and return standardized error responses.

Exception Handling

The application implements centralized exception handling to provide consistent API error responses.

Handled scenarios include:

Resource not found
Invalid request data
Authentication failure
Authorization failure
Duplicate resources
Invalid or expired refresh tokens
Other application exceptions
HTTPS

HTTPS is enabled for secure communication between clients and the application.

The application uses a PKCS12 keystore for SSL configuration.

Local application URL:

https://localhost:8000

For local development, a self-signed certificate is used.

CORS

Cross-Origin Resource Sharing is configured to control requests coming from different origins.

The configuration allows the application to securely handle cross-origin API requests while maintaining security controls.

Swagger / OpenAPI

Swagger/OpenAPI is integrated for API documentation and interactive API testing.

After starting the application, Swagger UI is available at:

https://localhost:8000/swagger-ui/index.html

Swagger provides:

Available API endpoints
Request and response models
API parameters
Authentication support
Interactive API testing
Testing

The project includes both unit and integration testing.

Unit Testing

JUnit 5 and Mockito are used for testing service and business logic in isolation.

Integration Testing

Spring Boot Test is used to verify the interaction between application components.

H2 in-memory database is used for integration testing to keep tests isolated from the MySQL database.

Run Tests

Windows:

.\mvnw.cmd test
Build

To clean and build the application:

.\mvnw.cmd clean package -DskipTests

The generated JAR file will be available inside:

target/
Running the Application Locally
Prerequisites

Make sure the following are installed:

Java 17 or higher
MySQL
Maven
Docker Desktop
Run Using Maven

Windows:

.\mvnw.cmd spring-boot:run

The application will start on:

https://localhost:8000
Docker

The project includes Docker support using Dockerfile and Docker Compose.

Build and Start Containers
docker compose up --build
Run Containers in Background
docker compose up -d --build
Stop Containers
docker compose down

Docker Compose is used to manage the application and database services.

Project Structure
Java-Backend-Developer/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── developer/
│   │   │           ├── config/
│   │   │           ├── controller/
│   │   │           ├── dto/
│   │   │           ├── entity/
│   │   │           ├── exception/
│   │   │           ├── repository/
│   │   │           ├── security/
│   │   │           └── service/
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       └── keystore.p12
│   │
│   └── test/
│       └── java/
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
Configuration

Application configuration is maintained in:

src/main/resources/application.properties

The application configuration includes:

Database configuration
JPA/Hibernate configuration
Server configuration
JWT configuration
HTTPS configuration

For production environments, sensitive credentials and secrets should be provided through environment variables or a secure configuration mechanism instead of being hard-coded.

API Response Format

The application follows JSON-based request and response formats.

Successful authentication response example:

{
  "accessToken": "access-token",
  "refreshToken": "refresh-token",
  "tokenType": "Bearer"
}
Security Considerations

The application implements the following security measures:

JWT-based authentication
Refresh token rotation
Role-based authorization
Password-based authentication
HTTPS
Input validation
CORS configuration
Centralized exception handling
Performance Considerations

The application includes:

Pagination for collection endpoints
Database indexing for frequently queried fields
Efficient database access through Spring Data JPA
Lazy loading where appropriate
Development Guidelines

The project follows clean code and layered architecture principles.

Responsibilities are separated across:

Controller
Service
Repository
Entity
DTO
Security
Exception Handling

This structure improves maintainability, readability, testing, and future scalability.
