package com.example.FresherManage.service;
import com.example.FresherManage.Dto.request.PermissionRequest;
import com.example.FresherManage.Dto.response.PermissionResponse;
import com.example.FresherManage.domain.Entity.Permission;
import com.example.FresherManage.Mapper.PermissionMapper;
import com.example.FresherManage.repository.PermissionRepository;
import com.example.FresherManage.Service.impl.PermissionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PermissionServiceTest {

    @InjectMocks
    private PermissionServiceImpl permissionService;

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private PermissionMapper permissionMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePermission() {
        // Arrange
        PermissionRequest request = PermissionRequest.builder()
                .name("READ")
                .description("Read permission")
                .build();

        Permission permission = Permission.builder()
                .name("READ")
                .description("Read permission")
                .build();

        PermissionResponse response = PermissionResponse.builder()
                .name("READ")
                .description("Read permission")
                .build();

        when(permissionMapper.toPermission(request)).thenReturn(permission);
        when(permissionRepository.save(permission)).thenReturn(permission);
        when(permissionMapper.toPermissionResponse(permission)).thenReturn(response);

        // Act
        PermissionResponse result = permissionService.create(request);

        // Assert
        assertEquals("READ", result.getName());
        assertEquals("Read permission", result.getDescription());
        verify(permissionMapper, times(1)).toPermission(request);
        verify(permissionRepository, times(1)).save(permission);
        verify(permissionMapper, times(1)).toPermissionResponse(permission);
    }

    @Test
    void testGetAllPermissions() {
        // Arrange
        Permission permission1 = Permission.builder()
                .name("READ")
                .description("Read permission")
                .build();

        Permission permission2 = Permission.builder()
                .name("WRITE")
                .description("Write permission")
                .build();

        List<Permission> permissions = List.of(permission1, permission2);

        PermissionResponse response1 = PermissionResponse.builder()
                .name("READ")
                .description("Read permission")
                .build();

        PermissionResponse response2 = PermissionResponse.builder()
                .name("WRITE")
                .description("Write permission")
                .build();

        when(permissionRepository.findAll()).thenReturn(permissions);
        when(permissionMapper.toPermissionResponse(permission1)).thenReturn(response1);
        when(permissionMapper.toPermissionResponse(permission2)).thenReturn(response2);

        // Act
        List<PermissionResponse> results = permissionService.getAll();

        // Assert
        assertEquals(2, results.size());
        assertEquals("READ", results.get(0).getName());
        assertEquals("WRITE", results.get(1).getName());
        verify(permissionRepository, times(1)).findAll();
        verify(permissionMapper, times(1)).toPermissionResponse(permission1);
        verify(permissionMapper, times(1)).toPermissionResponse(permission2);
    }

    @Test
    void testDeletePermission() {
        // Arrange
        String permissionName = "READ";

        // Act
        permissionService.delete(permissionName);

        // Assert
        verify(permissionRepository, times(1)).deleteById(permissionName);
    }
}

