package com.example.test1.Service.impl;

import java.util.HashSet;
import java.util.List;

import com.example.test1.Service.RoleService;
import org.springframework.stereotype.Service;

import com.example.test1.Dto.request.RoleRequest;
import com.example.test1.Dto.response.RoleResponse;
import com.example.test1.Mapper.RoleMapper;
import com.example.test1.repository.PermissionRepository;
import com.example.test1.repository.RoleRepository;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Service
@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IRoleService implements RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse createRole(RoleRequest roleRequest) {
        var role = roleMapper.toRole(roleRequest);

        var permission = permissionRepository.findAllById(roleRequest.getPermissions());
        role.setPermissions(new HashSet<>(permission));
        role = roleRepository.save(role);

        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public void deleteRole(String nameRole) {
        roleRepository.deleteById(nameRole);
    }
}
