package com.jsonplaceholder.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsonplaceholder.api.dto.LoginRequest;
import com.jsonplaceholder.api.dto.UserRequest;
import com.jsonplaceholder.api.model.Address;
import com.jsonplaceholder.api.model.Company;
import com.jsonplaceholder.api.model.Geo;
import com.jsonplaceholder.api.model.User;
import com.jsonplaceholder.api.security.JwtTokenProvider;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class TestUtil {
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    public static String asJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static MockHttpServletRequestBuilder createLoginRequest(LoginRequest loginRequest) {
        return post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginRequest));
    }
    
    public static MockHttpServletRequestBuilder createRegisterRequest(UserRequest userRequest) {
        return post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userRequest));
    }

    public static MockHttpServletRequestBuilder createGetUserRequest(Long userId) {
        return get("/api/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON);
    }

    public static MockHttpServletRequestBuilder createGetAllUsersRequest() {
        return get("/api/users")
                .contentType(MediaType.APPLICATION_JSON);
    }

    public static MockHttpServletRequestBuilder createUpdateUserRequest(Long userId, User user) {
        return put("/api/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user));
    }

    public static MockHttpServletRequestBuilder createDeleteUserRequest(Long userId) {
        return delete("/api/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON);
    }

    public static User createTestUser() {
        User user = new User();
        user.setName("Test User");
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setPhone("123-456-7890");
        user.setWebsite("test.com");

        // Create and set address
        Address address = new Address();
        address.setStreet("Test Street");
        address.setSuite("Suite 123");
        address.setCity("Test City");
        address.setZipcode("12345");

        Geo geo = new Geo();
        geo.setLat("12.3456");
        geo.setLng("78.9012");
        address.setGeo(geo);

        user.setAddress(address);

        // Create and set company
        Company company = new Company();
        company.setName("Test Company");
        company.setCatchPhrase("Test Catch Phrase");
        company.setBs("Test BS");

        user.setCompany(company);

        return user;
    }

    public static User createUpdatedTestUser() {
        User user = createTestUser();
        user.setName("Updated Test User");
        user.setEmail("updated@example.com");
        user.setPhone("987-654-3210");
        user.setWebsite("updated.com");
        
        Address address = user.getAddress();
        address.setStreet("Updated Street");
        address.setSuite("Suite 456");
        address.setCity("Updated City");
        address.setZipcode("54321");
        
        Geo geo = address.getGeo();
        geo.setLat("98.7654");
        geo.setLng("32.1098");
        
        Company company = user.getCompany();
        company.setName("Updated Company");
        company.setCatchPhrase("Updated Catch Phrase");
        company.setBs("Updated BS");
        
        return user;
    }

    public static String generateTestToken(JwtTokenProvider tokenProvider, String username) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username,
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
        return tokenProvider.generateToken(authentication);
    }
} 