package com.example.movieapi.mapper;


import com.example.movieapi.dto.UserDto.UserCreatedRequest;
import com.example.movieapi.dto.UserDto.UserProfileResponse;
import com.example.movieapi.dto.UserDto.UserUpdateRequest;
import com.example.movieapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "isEmailVerified", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUserFromRequest(UserCreatedRequest userCreatedRequest);

    @Mapping(target = "roles", source = "roles")
    UserProfileResponse toUserProfileResponse(User user);

    List<UserProfileResponse> toUserProfileResponseList(List<User> users);

    void updateUser(
            UserUpdateRequest request,
            @MappingTarget User user
    );

    default java.util.Set<String> mapRoles(java.util.Set<com.example.movieapi.entity.Role> roles) {
        return roles.stream()
                .map(role -> role.getName())
                .collect(java.util.stream.Collectors.toSet());
    }
}
