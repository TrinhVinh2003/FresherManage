package com.example.FresherManage.Controller;


import com.example.FresherManage.Dto.request.ApiResponse;
import com.example.FresherManage.Dto.request.PermissionRequest;
import com.example.FresherManage.Dto.response.PermissionResponse;
import com.example.FresherManage.Service.impl.PermissionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class PermissionControllerTest {

    @Mock
    private PermissionServiceImpl permissionService;

    @InjectMocks
    private PermissionController permissionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePermission() {
        // Prepare mock data
        PermissionRequest request = new PermissionRequest("READ", "Read permission");
        PermissionResponse response = new PermissionResponse("READ", "Read permission");

        // Mocking the service method
        when(permissionService.create(request)).thenReturn(response);

        // Test controller
        ApiResponse<PermissionResponse> apiResponse = permissionController.create(request);

        // Verify the result
        assertNotNull(apiResponse);
        assertEquals(response, apiResponse.getResult());
        verify(permissionService, times(1)).create(request);
    }

    @Test
    void testGetAllPermissions() {
        // Prepare mock data
        PermissionResponse perm1 = new PermissionResponse("READ", "Read permission");
        PermissionResponse perm2 = new PermissionResponse("WRITE", "Write permission");
        List<PermissionResponse> permissions = Arrays.asList(perm1, perm2);

        // Mocking the service method
        when(permissionService.getAll()).thenReturn(permissions);

        // Test controller
        ApiResponse<List<PermissionResponse>> apiResponse = permissionController.getAll();

        // Verify the result
        assertNotNull(apiResponse);
        assertEquals(permissions, apiResponse.getResult());
        verify(permissionService, times(1)).getAll();
    }

    @Test
    void testDeletePermission() {
        // Prepare mock data
        String permissionId = "READ";
        doNothing().when(permissionService).delete(permissionId);

        // Test controller
        ApiResponse<Void> apiResponse = permissionController.DeleteId(permissionId);

        // Verify the result
        assertNotNull(apiResponse);
        verify(permissionService, times(1)).delete(permissionId);
    }
}