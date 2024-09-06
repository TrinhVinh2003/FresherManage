package com.example.FresherManage.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.FresherManage.Dto.request.RoleRequest;
import com.example.FresherManage.Dto.response.RoleResponse;
import com.example.FresherManage.domain.Entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
