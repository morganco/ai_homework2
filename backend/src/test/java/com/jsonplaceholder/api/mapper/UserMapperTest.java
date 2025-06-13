package com.jsonplaceholder.api.mapper;

import com.jsonplaceholder.api.dto.UserRequest;
import com.jsonplaceholder.api.dto.UserResponse;
import com.jsonplaceholder.api.model.Address;
import com.jsonplaceholder.api.model.Company;
import com.jsonplaceholder.api.model.Geo;
import com.jsonplaceholder.api.model.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void toResponse_ShouldMapUserToResponse() {
        // Given
        User user = createTestUser();

        // When
        UserResponse response = mapper.toResponse(user);

        // Then
        assertNotNull(response);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getName(), response.getName());
        assertEquals(user.getUsername(), response.getUsername());
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(user.getPhone(), response.getPhone());
        assertEquals(user.getWebsite(), response.getWebsite());
        assertAddressEquals(user.getAddress(), response.getAddress());
        assertCompanyEquals(user.getCompany(), response.getCompany());
    }

    @Test
    void toEntity_ShouldMapRequestToUser() {
        // Given
        UserRequest request = createTestUserRequest();

        // When
        User user = mapper.toEntity(request);

        // Then
        assertNotNull(user);
        assertNull(user.getId()); // ID should not be mapped
        assertEquals(request.getName(), user.getName());
        assertEquals(request.getUsername(), user.getUsername());
        assertEquals(request.getEmail(), user.getEmail());
        assertEquals(request.getPassword(), user.getPassword());
        assertEquals(request.getPhone(), user.getPhone());
        assertEquals(request.getWebsite(), user.getWebsite());
        assertAddressEquals(request.getAddress(), user.getAddress());
        assertCompanyEquals(request.getCompany(), user.getCompany());
    }

    @Test
    void updateEntityFromRequest_ShouldUpdateUser() {
        // Given
        User user = createTestUser();
        UserRequest request = createTestUserRequest();

        // When
        mapper.updateEntityFromRequest(request, user);

        // Then
        assertEquals(request.getName(), user.getName());
        assertEquals(request.getUsername(), user.getUsername());
        assertEquals(request.getEmail(), user.getEmail());
        assertEquals(request.getPassword(), user.getPassword());
        assertEquals(request.getPhone(), user.getPhone());
        assertEquals(request.getWebsite(), user.getWebsite());
        assertAddressEquals(request.getAddress(), user.getAddress());
        assertCompanyEquals(request.getCompany(), user.getCompany());
    }

    private User createTestUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setPhone("123-456-7890");
        user.setWebsite("test.com");

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

        Company company = new Company();
        company.setName("Test Company");
        company.setCatchPhrase("Test Catch Phrase");
        company.setBs("Test BS");

        user.setCompany(company);

        return user;
    }

    private UserRequest createTestUserRequest() {
        UserRequest request = new UserRequest();
        request.setName("New User");
        request.setUsername("newuser");
        request.setEmail("new@example.com");
        request.setPassword("newpassword");
        request.setPhone("987-654-3210");
        request.setWebsite("new.com");

        Address address = new Address();
        address.setStreet("New Street");
        address.setSuite("Suite 456");
        address.setCity("New City");
        address.setZipcode("54321");

        Geo geo = new Geo();
        geo.setLat("98.7654");
        geo.setLng("32.1098");
        address.setGeo(geo);

        request.setAddress(address);

        Company company = new Company();
        company.setName("New Company");
        company.setCatchPhrase("New Catch Phrase");
        company.setBs("New BS");

        request.setCompany(company);

        return request;
    }

    private void assertAddressEquals(Address expected, Address actual) {
        assertNotNull(actual);
        assertEquals(expected.getStreet(), actual.getStreet());
        assertEquals(expected.getSuite(), actual.getSuite());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getZipcode(), actual.getZipcode());
        assertGeoEquals(expected.getGeo(), actual.getGeo());
    }

    private void assertGeoEquals(Geo expected, Geo actual) {
        assertNotNull(actual);
        assertEquals(expected.getLat(), actual.getLat());
        assertEquals(expected.getLng(), actual.getLng());
    }

    private void assertCompanyEquals(Company expected, Company actual) {
        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getCatchPhrase(), actual.getCatchPhrase());
        assertEquals(expected.getBs(), actual.getBs());
    }
} 