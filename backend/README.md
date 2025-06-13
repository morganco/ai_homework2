# JSONPlaceholder API Clone

This is a Spring Boot implementation of the JSONPlaceholder API with extended features including JWT authentication and PostgreSQL database support.

## Features

- Full REST API implementation
- JWT-based authentication
- PostgreSQL database integration
- Docker and Docker Compose support
- Hibernate ORM
- Maven build system

## Prerequisites

- Java 17 or higher
- Maven
- Docker and Docker Compose
- PostgreSQL (if running locally)

## Getting Started

### Using Docker Compose (Recommended)

1. Clone the repository
2. Navigate to the project directory
3. Run the following command:
   ```bash
   docker-compose up --build
   ```

The application will be available at `http://localhost:8080/api`

### Manual Setup

1. Clone the repository
2. Navigate to the project directory
3. Create a PostgreSQL database named `jsonplaceholder`
4. Update the `application.yml` with your database credentials
5. Run the following commands:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## API Endpoints

### Authentication

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and get JWT token

### Users

- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

## Security

All endpoints except `/api/auth/**` require JWT authentication. Include the JWT token in the Authorization header:

```
Authorization: Bearer <your_jwt_token>
```

## Development

The project uses:
- Spring Boot 3.2.3
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT for authentication
- Maven for dependency management

## License

MIT 