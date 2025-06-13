# User Management System

A full-stack application for managing users with a modern web interface and RESTful API.

## Tech Stack

### Backend
- Java 17
- Spring Boot 3.x
- Spring Security with JWT
- PostgreSQL 15
- Maven
- JUnit 5 for testing

### Frontend
- Vue.js 3
- TypeScript
- Pinia for state management
- Jest for unit testing
- SCSS for styling
- Axios for HTTP requests

### Infrastructure
- Docker & Docker Compose
- Nginx as reverse proxy
- PostgreSQL for data persistence

## Features

### User Management
- User registration and authentication
- JWT-based secure authentication
- User profile management
- User search and filtering
- User deletion

### Security
- Password encryption
- JWT token-based authentication
- Role-based access control
- Input validation
- CORS configuration

### UI/UX
- Responsive design
- Modern table layout
- Search functionality
- User detail modal
- Loading states
- Error handling

## Prerequisites

- Docker and Docker Compose
- Node.js 18+ (for local frontend development)
- Java 17+ (for local backend development)
- Maven (for local backend development)

## Getting Started

### Using Docker (Recommended)

1. Clone the repository:
```bash
git clone <repository-url>
cd <repository-name>
```

2. Start all services:
```bash
docker-compose up --build
```

The application will be available at:
- Frontend: http://localhost
- Backend API: http://localhost/api
- PostgreSQL: localhost:5432

### Local Development

#### Backend

1. Navigate to the backend directory:
```bash
cd backend
```

2. Run the application:
```bash
mvn spring-boot:run
```

The backend will be available at http://localhost:8080

#### Frontend

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm run serve
```

The frontend will be available at http://localhost

## Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
npm run test:unit
```

## API Documentation

### Authentication Endpoints
- POST /api/auth/register - Register a new user
- POST /api/auth/login - Login and get JWT token

### User Endpoints
- GET /api/users - Get all users
- GET /api/users/{id} - Get user by ID
- PUT /api/users/{id} - Update user
- DELETE /api/users/{id} - Delete user

## Project Structure

```
.
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── stores/
│   │   ├── types/
│   │   └── styles/
│   ├── tests/
│   └── package.json
├── docker-compose.yml
└── README.md
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.