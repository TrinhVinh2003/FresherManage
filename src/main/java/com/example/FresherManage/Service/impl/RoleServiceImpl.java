package com.example.FresherManage.Service.impl;

import java.util.HashSet;
import java.util.List;

import com.example.FresherManage.Service.RoleService;
import org.springframework.stereotype.Service;

import com.example.FresherManage.Dto.request.RoleRequest;
import com.example.FresherManage.Dto.response.RoleResponse;
import com.example.FresherManage.Mapper.RoleMapper;
import com.example.FresherManage.repository.PermissionRepository;
import com.example.FresherManage.repository.RoleRepository;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Service
@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
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
