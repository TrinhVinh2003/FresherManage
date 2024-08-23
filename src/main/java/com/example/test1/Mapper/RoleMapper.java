package com.example.test1.Mapper;

import com.example.test1.Dto.request.RoleRequest;
import com.example.test1.Dto.response.RoleResponse;
import com.example.test1.Entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions",ignore = true)
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);
}
