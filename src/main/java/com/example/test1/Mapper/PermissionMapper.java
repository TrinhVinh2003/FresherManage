package com.example.test1.Mapper;


import com.example.test1.Dto.request.PermissionRequest;
import com.example.test1.Dto.response.PermissionResponse;
import com.example.test1.Entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest permissionRequest);

    PermissionResponse toPermissionResponse(Permission permission);

}
