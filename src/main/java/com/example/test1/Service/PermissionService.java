package com.example.test1.Service;

import com.example.test1.Dto.request.PermissionRequest;
import com.example.test1.Dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse create(PermissionRequest request);

    List<PermissionResponse> getAll();

    void delete(String permission);
}
