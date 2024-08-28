package com.example.FresherManage.Service;

import com.example.FresherManage.Dto.request.PermissionRequest;
import com.example.FresherManage.Dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse create(PermissionRequest request);

    List<PermissionResponse> getAll();

    void delete(String permission);
}
