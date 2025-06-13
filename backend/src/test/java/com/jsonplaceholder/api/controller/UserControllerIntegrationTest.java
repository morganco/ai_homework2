package com.jsonplaceholder.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;
    private String authToken;
    private UserRequest testUserRequest;

    @BeforeEach
    void setUp() throws Exception {
        // Clean up the database
        userRepository.deleteAll();

        // Create test user with unique email and username
        String uniqueId = UUID.randomUUID().toString();
        testUser = new User();
        testUser.setName("Test User");
        testUser.setUsername("testuser_" + uniqueId);
        testUser.setEmail(uniqueId + "@example.com");
        testUser.setPassword(passwordEncoder.encode("password123"));
        testUser.setPhone("123-456-7890");
        testUser.setWebsite("test.com");
        testUser = userRepository.save(testUser);

        // Create UserDetails for authentication
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                testUser.getEmail(),
                testUser.getPassword(),
                java.util.Collections.singletonList(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_USER"))
        );

        // Create Authentication object
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        // Generate auth token
        authToken = tokenProvider.generateToken(authentication);

        // Create test user request for updates
        testUserRequest = new UserRequest();
        testUserRequest.setName("Updated User");
        testUserRequest.setUsername("updateduser_" + uniqueId);
        testUserRequest.setEmail("updated_" + uniqueId + "@example.com");
        testUserRequest.setPassword("newpassword");
        testUserRequest.setPhone("987-654-3210");
        testUserRequest.setWebsite("updated.com");
    }

    @Test
    @WithMockUser
    void getAllUsers_ShouldReturnUsers() throws Exception {
        mockMvc.perform(get("/api/users")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].username").exists())
                .andExpect(jsonPath("$[0].password").doesNotExist());
    }

    @Test
    @WithMockUser
    void getUserById_WhenUserExists_ShouldReturnUser() throws Exception {
        mockMvc.perform(get("/api/users/{id}", testUser.getId())
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testUser.getId()))
                .andExpect(jsonPath("$.username").value(testUser.getUsername()))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void getUserById_WhenUserDoesNotExist_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/users/999")
                .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found with id: 999"));
    }

    @Test
    void updateUser_WhenUserExists_ShouldUpdateUser() throws Exception {
        // Create a new user request with updated data
        UserRequest updateRequest = new UserRequest();
        updateRequest.setName("Updated Name");
        updateRequest.setUsername("updateduser"); // Use a shorter username
        updateRequest.setEmail("updated@example.com");
        updateRequest.setPassword("newpassword");
        updateRequest.setPhone("987-654-3210");
        updateRequest.setWebsite("updated.com");

        mockMvc.perform(put("/api/users/{id}", testUser.getId())
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updateRequest.getName()))
                .andExpect(jsonPath("$.username").value(updateRequest.getUsername()))
                .andExpect(jsonPath("$.email").value(updateRequest.getEmail()))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    @WithMockUser
    void deleteUser_WhenUserExists_ShouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", testUser.getId())
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk());

        assertFalse(userRepository.existsById(testUser.getId()));
    }
} 