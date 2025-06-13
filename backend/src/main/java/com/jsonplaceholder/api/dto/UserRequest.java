package com.jsonplaceholder.api.dto;

import com.jsonplaceholder.api.model.Address;
import com.jsonplaceholder.api.model.Company;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Valid
    private Address address;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Website is required")
    private String website;

    @Valid
    private Company company;
} 