package com.example.FresherManage.Controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.FresherManage.Dto.request.RoleRequest;
import com.example.FresherManage.Dto.response.RoleResponse;
import com.example.FresherManage.Service.impl.RoleServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RoleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RoleServiceImpl roleService;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    @Test
    public void testCreateRole() throws Exception {
        RoleRequest roleRequest = RoleRequest.builder()
                .name("Admin")
                .description("Administrator role")
                .permissions(Set.of("READ", "WRITE"))
                .build();

        RoleResponse roleResponse = RoleResponse.builder()
                .name("Admin")
                .description("Administrator role")
                .permissions(Collections.emptySet())
                .build();

        when(roleService.createRole(any(RoleRequest.class))).thenReturn(roleResponse);

        mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(roleRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.name", is(roleResponse.getName())))
                .andExpect(jsonPath("$.result.description", is(roleResponse.getDescription())))
                .andExpect(jsonPath("$.result.permissions", is(Collections.emptyList())));
    }

    @Test
    public void testGetAllRoles() throws Exception {
        RoleResponse roleResponse = RoleResponse.builder()
                .name("Admin")
                .description("Administrator role")
                .permissions(Collections.emptySet())
                .build();

        when(roleService.getAll()).thenReturn(Collections.singletonList(roleResponse));

        mockMvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result[0].name", is(roleResponse.getName())))
                .andExpect(jsonPath("$.result[0].description", is(roleResponse.getDescription())))
                .andExpect(jsonPath("$.result[0].permissions", is(Collections.emptyList())));
    }

    @Test
    public void testDeleteRole() throws Exception {
        mockMvc.perform(delete("/roles/Admin")).andExpect(status().isOk());
    }
}
