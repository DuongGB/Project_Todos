# Project_Todos
A Spring Boot backend application for managing todo tasks with user authentication, caching, and PostgreSQL database.

## 🚀 Features

- **User Authentication**: JWT-based authentication with access and refresh tokens
- **Task Management**: Full CRUD operations for todo tasks
- **Checklist Support**: Each task can have multiple checklist items
- **Caching**: Redis caching for improved performance
- **Security**: Spring Security with method-level authorization
- **Database**: PostgreSQL with JPA/Hibernate
- **RESTful API**: Clean REST endpoints with standardized responses

## 🛠 Tech Stack

- **Java 17**
- **Spring Boot 3.5.4**
- **Spring Security** - Authentication & Authorization
- **Spring Data JPA** - Data persistence
- **PostgreSQL** - Primary database
- **Redis** - Caching layer
- **JWT** - Token-based authentication
- **Lombok** - Reducing boilerplate code
- **Maven** - Dependency management

## 📋 Prerequisites

Before running this application, make sure you have:

- Java 17 or higher
- Maven 3.6+
- PostgreSQL database
- Redis server
- Your favorite IDE (IntelliJ IDEA recommended)

## ⚙️ Configuration

### Database Setup

1. Create a PostgreSQL database named `tododb`
2. Update database credentials in [`application.yaml`](src/main/resources/application.yaml):

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/tododb
    username: postgres
    password: ${PASSWORD}
```

### Redis Setup

Ensure Redis is running on default port 6379, or update the configuration in [`application.yaml`](src/main/resources/application.yaml):

```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
      timeout: 60000
```

## 🚀 Running the Application

### Using Maven Wrapper

```bash
# On Unix/Mac
./mvnw spring-boot:run

# On Windows
mvnw.cmd spring-boot:run
```

### Using Maven

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`
