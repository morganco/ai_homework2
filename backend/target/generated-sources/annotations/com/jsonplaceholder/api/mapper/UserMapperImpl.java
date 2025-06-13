package com.jsonplaceholder.api.mapper;

import com.jsonplaceholder.api.dto.UserRequest;
import com.jsonplaceholder.api.dto.UserResponse;
import com.jsonplaceholder.api.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-13T14:54:57+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setAddress( user.getAddress() );
        userResponse.setCompany( user.getCompany() );
        userResponse.setEmail( user.getEmail() );
        userResponse.setId( user.getId() );
        userResponse.setName( user.getName() );
        userResponse.setPhone( user.getPhone() );
        userResponse.setUsername( user.getUsername() );
        userResponse.setWebsite( user.getWebsite() );

        return userResponse;
    }

    @Override
    public List<UserResponse> toResponseList(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserResponse> list = new ArrayList<UserResponse>( users.size() );
        for ( User user : users ) {
            list.add( toResponse( user ) );
        }

        return list;
    }

    @Override
    public User toEntity(UserRequest request) {
        if ( request == null ) {
            return null;
        }

        User user = new User();

        user.setAddress( request.getAddress() );
        user.setCompany( request.getCompany() );
        user.setEmail( request.getEmail() );
        user.setName( request.getName() );
        user.setPassword( request.getPassword() );
        user.setPhone( request.getPhone() );
        user.setUsername( request.getUsername() );
        user.setWebsite( request.getWebsite() );

        return user;
    }

    @Override
    public void updateEntityFromRequest(UserRequest request, User user) {
        if ( request == null ) {
            return;
        }

        user.setAddress( request.getAddress() );
        user.setCompany( request.getCompany() );
        user.setEmail( request.getEmail() );
        user.setName( request.getName() );
        user.setPassword( request.getPassword() );
        user.setPhone( request.getPhone() );
        user.setUsername( request.getUsername() );
        user.setWebsite( request.getWebsite() );
    }
}
