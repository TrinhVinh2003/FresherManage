package com.example.test1.Service.impl;

import java.util.List;

import com.example.test1.Service.PermissionService;
import org.springframework.stereotype.Service;

import com.example.test1.Dto.request.PermissionRequest;
import com.example.test1.Dto.response.PermissionResponse;
import com.example.test1.Entity.Permission;
import com.example.test1.Mapper.PermissionMapper;
import com.example.test1.repository.PermissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IPermissionService implements PermissionService {
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
