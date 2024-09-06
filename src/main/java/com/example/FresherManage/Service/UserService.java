package com.example.FresherManage.Service;

import com.example.FresherManage.Dto.request.UserCreationRequest;
import com.example.FresherManage.Dto.request.UserUpdateRequest;
import com.example.FresherManage.Dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreationRequest request);

    UserResponse updateUser(String userId, UserUpdateRequest request);

    void deleteUser(String userId);

    UserResponse getUser(String id);

    List<UserResponse> getUsers();

    UserResponse getMyInfo();
}
