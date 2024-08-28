package com.example.FresherManage.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.FresherManage.Dto.request.ApiResponse;
import com.example.FresherManage.Dto.request.PermissionRequest;
import com.example.FresherManage.Dto.response.PermissionResponse;
import com.example.FresherManage.Service.impl.PermissionServiceImpl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionServiceImpl permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiResponse<Void> DeleteId(@PathVariable("permission") String id) {
        permissionService.delete(id);
        return ApiResponse.<Void>builder().build();
    }
}
