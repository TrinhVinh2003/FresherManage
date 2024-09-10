package com.example.FresherManage.service;
import com.example.FresherManage.Dto.request.RoleRequest;
import com.example.FresherManage.Dto.response.RoleResponse;
import com.example.FresherManage.domain.Entity.Role;
import com.example.FresherManage.domain.Entity.Permission;
import com.example.FresherManage.Mapper.RoleMapper;
import com.example.FresherManage.repository.PermissionRepository;
import com.example.FresherManage.repository.RoleRepository;
import com.example.FresherManage.Service.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoleServiceTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private RoleMapper roleMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRole() {
        // Arrange
        RoleRequest roleRequest = RoleRequest.builder()
                .name("ADMIN")
                .description("Administrator role")
                .permissions(Set.of("READ", "WRITE"))
                .build();

        Role role = Role.builder()
                .name("ADMIN")
                .description("Administrator role")
                .permissions(new HashSet<>())
                .build();

        RoleResponse roleResponse = RoleResponse.builder()
                .name("ADMIN")
                .description("Administrator role")
                .permissions(Set.of())
                .build();

        Permission readPermission = Permission.builder()
                .name("READ")
                .description("Read permission")
                .build();

        Permission writePermission = Permission.builder()
                .name("WRITE")
                .description("Write permission")
                .build();

        when(roleMapper.toRole(roleRequest)).thenReturn(role);
        when(permissionRepository.findAllById(roleRequest.getPermissions()))
                .thenReturn(List.of(readPermission, writePermission));
        when(roleRepository.save(role)).thenReturn(role);
        when(roleMapper.toRoleResponse(role)).thenReturn(roleResponse);

        // Act
        RoleResponse result = roleService.createRole(roleRequest);

        // Assert
        assertEquals("ADMIN", result.getName());
        assertEquals("Administrator role", result.getDescription());
        assertEquals(Set.of(), result.getPermissions());  // Assuming permissions are transformed correctly
        verify(roleMapper, times(1)).toRole(roleRequest);
        verify(permissionRepository, times(1)).findAllById(roleRequest.getPermissions());
        verify(roleRepository, times(1)).save(role);
        verify(roleMapper, times(1)).toRoleResponse(role);
    }

    @Test
    void testGetAllRoles() {
        // Arrange
        Role role1 = Role.builder()
                .name("USER")
                .description("User role")
                .permissions(new HashSet<>())
                .build();

        Role role2 = Role.builder()
                .name("ADMIN")
                .description("Administrator role")
                .permissions(new HashSet<>())
                .build();

        RoleResponse response1 = RoleResponse.builder()
                .name("USER")
                .description("User role")
                .permissions(Set.of())
                .build();

        RoleResponse response2 = RoleResponse.builder()
                .name("ADMIN")
                .description("Administrator role")
                .permissions(Set.of())
                .build();

        when(roleRepository.findAll()).thenReturn(List.of(role1, role2));
        when(roleMapper.toRoleResponse(role1)).thenReturn(response1);
        when(roleMapper.toRoleResponse(role2)).thenReturn(response2);

        // Act
        List<RoleResponse> results = roleService.getAll();

        // Assert
        assertEquals(2, results.size());
        assertEquals("USER", results.get(0).getName());
        assertEquals("ADMIN", results.get(1).getName());
        verify(roleRepository, times(1)).findAll();
        verify(roleMapper, times(1)).toRoleResponse(role1);
        verify(roleMapper, times(1)).toRoleResponse(role2);
    }

    @Test
    void testDeleteRole() {
        // Arrange
        String roleName = "ADMIN";

        // Act
        roleService.deleteRole(roleName);

        // Assert
        verify(roleRepository, times(1)).deleteById(roleName);
    }
}
