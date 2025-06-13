package com.jsonplaceholder.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsonplaceholder.api.dto.LoginRequest;
import com.jsonplaceholder.api.dto.UserRequest;
import com.jsonplaceholder.api.model.User;
import com.jsonplaceholder.api.repository.UserRepository;
import com.jsonplaceholder.api.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;
    private String authToken;
    private UserRequest testUserRequest;
    private LoginRequest testLoginRequest;

    @BeforeEach
    void setUp() throws Exception {
        // Clean up the database
        userRepository.deleteAll();

        // Create test user with unique email and username
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        testUser = new User();
        testUser.setName("Test User");
        testUser.setUsername("test_" + uniqueId);
        testUser.setEmail(uniqueId + "@example.com");
        testUser.setPassword(passwordEncoder.encode("password123"));
        testUser.setPhone("123-456-7890");
        testUser.setWebsite("test.com");
        testUser = userRepository.save(testUser);

        // Create UserDetails for authentication
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                testUser.getUsername(),
                testUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        // Create Authentication object
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        // Generate auth token
        authToken = tokenProvider.generateToken(authentication);

        // Create test user request for registration
        testUserRequest = new UserRequest();
        testUserRequest.setName("New User");
        testUserRequest.setUsername("new_" + uniqueId);
        testUserRequest.setEmail("new_" + uniqueId + "@example.com");
        testUserRequest.setPassword("newpassword");
        testUserRequest.setPhone("987-654-3210");
        testUserRequest.setWebsite("new.com");

        // Create test login request
        testLoginRequest = new LoginRequest();
        testLoginRequest.setUsername(testUser.getUsername());
        testLoginRequest.setPassword("password123");
    }

    @Test
    void login_WithValidCredentials_ShouldReturnToken() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testLoginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void login_WithInvalidCredentials_ShouldReturnUnauthorized() throws Exception {
        LoginRequest invalidLogin = new LoginRequest();
        invalidLogin.setUsername(testUser.getUsername());
        invalidLogin.setPassword("wrongpassword");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidLogin)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void register_WithValidData_ShouldCreateUser() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUserRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(testUserRequest.getUsername()))
                .andExpect(jsonPath("$.email").value(testUserRequest.getEmail()))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void register_WithExistingUsername_ShouldReturnBadRequest() throws Exception {
        UserRequest existingUserRequest = new UserRequest();
        existingUserRequest.setName("Existing User");
        existingUserRequest.setUsername(testUser.getUsername());
        existingUserRequest.setEmail("existing@example.com");
        existingUserRequest.setPassword("password123");
        existingUserRequest.setPhone("123-456-7890");
        existingUserRequest.setWebsite("existing.com");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingUserRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Username is already taken!"));
    }

    @Test
    void register_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        UserRequest invalidRequest = new UserRequest();
        invalidRequest.setName("Invalid User");
        invalidRequest.setUsername("");
        invalidRequest.setEmail("invalid-email");
        invalidRequest.setPassword("123");
        invalidRequest.setPhone("123");
        invalidRequest.setWebsite("invalid");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").exists())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors[?(@.field == 'username')]").exists())
                .andExpect(jsonPath("$.errors[?(@.field == 'email')]").exists());
    }
} 