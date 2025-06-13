# JSONPlaceholder Frontend

This is the frontend application for the JSONPlaceholder API project, built with Vue 3, TypeScript, and Pinia.

## Project Setup

```bash
# Install dependencies
npm install

# Start development server
npm run serve

# Build for production
npm run build

# Run linter
npm run lint

# Run unit tests
npm run test:unit
```

## Project Structure

```
frontend/
├── src/
│   ├── components/     # Vue components
│   ├── stores/         # Pinia stores
│   ├── types/          # TypeScript type definitions
│   ├── styles/         # Global styles and variables
│   ├── router/         # Vue Router configuration
│   └── App.vue         # Root component
├── public/             # Static assets
├── tests/              # Test files
└── package.json        # Project dependencies and scripts
```

## Development Guidelines

1. **TypeScript**
   - Use TypeScript for all new code
   - Define interfaces for all data structures
   - Avoid using `any` type
   - Use type inference where possible

2. **Vue Components**
   - Use Composition API with `<script setup>`
   - Keep components small and focused
   - Use props validation
   - Emit typed events

3. **State Management**
   - Use Pinia for global state
   - Keep stores modular
   - Use TypeScript for store state and actions

4. **Styling**
   - Use SCSS for styling
   - Follow BEM naming convention
   - Use CSS variables for theming
   - Keep styles scoped to components

5. **Testing**
   - Write unit tests for components
   - Test store actions and mutations
   - Use Vue Test Utils for component testing

6. **Code Style**
   - Follow ESLint rules
   - Use Prettier for formatting
   - Write meaningful commit messages
   - Document complex logic

## API Integration

The frontend communicates with the backend API at `http://localhost:8080/api`. The API endpoints are:

- `GET /api/users` - Get all users
- `GET /api/users/:id` - Get user by ID
- `POST /api/users` - Create new user
- `PUT /api/users/:id` - Update user
- `DELETE /api/users/:id` - Delete user

## Authentication

The application uses JWT authentication. The authentication endpoints are:

- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user

## Contributing

1. Create a new branch for your feature
2. Write tests for your changes
3. Ensure all tests pass
4. Submit a pull request

## License

MIT 