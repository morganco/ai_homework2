package com.jsonplaceholder.api.mapper;

import com.jsonplaceholder.api.dto.UserRequest;
import com.jsonplaceholder.api.dto.UserResponse;
import com.jsonplaceholder.api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    
    UserResponse toResponse(User user);
    
    List<UserResponse> toResponseList(List<User> users);
    
    @Mapping(target = "id", ignore = true)
    User toEntity(UserRequest request);
    
    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(UserRequest request, @MappingTarget User user);
} 