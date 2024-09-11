package com.example.FresherManage.Controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.FresherManage.Dto.request.ApiResponse;
import com.example.FresherManage.Dto.request.UserCreationRequest;
import com.example.FresherManage.Dto.request.UserUpdateRequest;
import com.example.FresherManage.Dto.response.UserDetailResponse;
import com.example.FresherManage.Dto.response.UserResponse;
import com.example.FresherManage.Service.impl.UserServiceImpl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    @Autowired
    UserServiceImpl userServiceimp;

    @PostMapping
    ApiResponse<UserResponse> createUser(
            @RequestBody @Valid
                    UserCreationRequest
                            request) { // @valid validate object dựa theo những cái role mà đc defind trong object đó
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userServiceimp.createUser(request));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("USERNAME : {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder()
                .result(userServiceimp.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {

        return ApiResponse.<UserResponse>builder()
                .result(userServiceimp.getUser(userId))
                .build();
    }

    @GetMapping("/myInfo")
    ApiResponse<UserDetailResponse> getMyInfo() {

        return ApiResponse.<UserDetailResponse>builder()
                .result(userServiceimp.getMyInfo())
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userServiceimp.updateUser(userId, request))
                .build();
    }
}
