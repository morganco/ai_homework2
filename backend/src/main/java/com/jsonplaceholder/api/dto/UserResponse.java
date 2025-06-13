package com.jsonplaceholder.api.dto;

import com.jsonplaceholder.api.model.Address;
import com.jsonplaceholder.api.model.Company;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;
} 