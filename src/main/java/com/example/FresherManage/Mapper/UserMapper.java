package com.example.FresherManage.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.FresherManage.Dto.request.UserCreationRequest;
import com.example.FresherManage.Dto.request.UserUpdateRequest;
import com.example.FresherManage.Dto.response.UserResponse;
import com.example.FresherManage.domain.Entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    //    @Mapping(source = "",target = "")
    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
    // mappingtarget : mapping object request -> user

}
