package com.example.FresherManage.Mapper;

import org.mapstruct.Mapper;

import com.example.FresherManage.Dto.request.PermissionRequest;
import com.example.FresherManage.Dto.response.PermissionResponse;
import com.example.FresherManage.Entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest permissionRequest);

    PermissionResponse toPermissionResponse(Permission permission);
}
