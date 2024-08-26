package com.example.test1.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.test1.Dto.request.ApiResponse;
import com.example.test1.Dto.request.PermissionRequest;
import com.example.test1.Dto.response.PermissionResponse;
import com.example.test1.Service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

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
