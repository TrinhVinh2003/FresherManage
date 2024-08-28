package com.example.FresherManage.Service.impl;

import java.util.List;

import com.example.FresherManage.Service.PermissionService;
import org.springframework.stereotype.Service;

import com.example.FresherManage.Dto.request.PermissionRequest;
import com.example.FresherManage.Dto.response.PermissionResponse;
import com.example.FresherManage.Entity.Permission;
import com.example.FresherManage.Mapper.PermissionMapper;
import com.example.FresherManage.repository.PermissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }

    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
