package com.example.test1.Service;

import com.example.test1.Dto.request.UserCreationRequest;
import com.example.test1.Dto.request.UserUpdateRequest;
import com.example.test1.Dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createRequest(UserCreationRequest request);

    UserResponse updateUser(String userId, UserUpdateRequest request);

    void deleteUser(String userId);

    UserResponse getUser(String id);

    List<UserResponse> getUsers();

    UserResponse getMyInfo();
}
