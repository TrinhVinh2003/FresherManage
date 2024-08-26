package com.example.test1.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.test1.Dto.request.UserCreationRequest;
import com.example.test1.Dto.request.UserUpdateRequest;
import com.example.test1.Dto.response.UserResponse;
import com.example.test1.Entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    //    @Mapping(source = "",target = "")
    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
    // mappingtarget : mapping object request -> user

}
