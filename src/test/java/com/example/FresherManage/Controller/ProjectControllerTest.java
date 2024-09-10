package com.example.FresherManage.Controller;


import com.example.FresherManage.Dto.request.ApiResponse;
import com.example.FresherManage.Dto.request.FresherProjectRequest;
import com.example.FresherManage.Dto.request.ProjectCreateRequest;
import com.example.FresherManage.Dto.response.FresherProjectReponse;
import com.example.FresherManage.Dto.response.ProjectResponse;
import com.example.FresherManage.Service.impl.ProjectServiceImpl;
import com.example.FresherManage.domain.Entity.Center;
import com.example.FresherManage.domain.Entity.Project;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = ProjectController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectServiceImpl projectService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProjectCreateRequest projectCreateRequest;
    private ProjectResponse projectResponse;
    private FresherProjectRequest fresherProjectRequest;
    private FresherProjectReponse fresherProjectReponse;

    @BeforeEach
    public void setUp() {
        projectCreateRequest = ProjectCreateRequest.builder()
                .name("Project A")
                .center("Center A")
                .manager("Manager A")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(6))
                .language("Java")
                .status(Project.ProjectStatus.ONGOING)
                .build();

        projectResponse = ProjectResponse.builder()
                .name("Project A")
                .center(Center.builder().name("Center").build())  // Assuming `Center` is not needed for these tests
                .manager("Manager A")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(6))
                .language("Java")
                .status(Project.ProjectStatus.ONGOING)
                .build();

        fresherProjectRequest = new FresherProjectRequest(); // Add properties if needed

        fresherProjectReponse = new FresherProjectReponse(); // Add properties if needed
    }

    @Test
    public void createProject_ReturnCreated() throws Exception {
        given(projectService.createProject(ArgumentMatchers.any())).willReturn(projectResponse);

        ResultActions response = mockMvc.perform(post("/project")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectCreateRequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.name").value(projectCreateRequest.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.manager").value(projectCreateRequest.getManager()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.language").value(projectCreateRequest.getLanguage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.status").value(projectCreateRequest.getStatus().name()));
    }

    @Test
    public void updateProject_ReturnUpdatedProject() throws Exception {
        Long projectId = 1L;
        given(projectService.updateProject(ArgumentMatchers.eq(projectId), ArgumentMatchers.any()))
                .willReturn(projectResponse);

        ResultActions response = mockMvc.perform(put("/project/{id}", projectId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectCreateRequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.name").value(projectCreateRequest.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.manager").value(projectCreateRequest.getManager()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.language").value(projectCreateRequest.getLanguage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.status").value(projectCreateRequest.getStatus().name()));
    }

    @Test
    public void deleteProject_ReturnOk() throws Exception {
        Long projectId = 1L;
        doNothing().when(projectService).deleteProject(projectId);

        ResultActions response = mockMvc.perform(delete("/project/{id}", projectId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getAll_ReturnProjectList() throws Exception {
        List<ProjectResponse> projectResponses = Collections.singletonList(projectResponse);
        given(projectService.getAll()).willReturn(projectResponses);

        ResultActions response = mockMvc.perform(get("/project")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].name").value(projectResponse.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].manager").value(projectResponse.getManager()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].language").value(projectResponse.getLanguage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].status").value(projectResponse.getStatus().name()));
    }

    @Test
    public void addFresher_ReturnUpdatedFresherProject() throws Exception {
        Long projectId = 1L;
        given(projectService.addFresherToProject(ArgumentMatchers.eq(projectId), ArgumentMatchers.any()))
                .willReturn(fresherProjectReponse);

        ResultActions response = mockMvc.perform(put("/project/addFresher/{id}", projectId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fresherProjectRequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").exists());
        // Add more specific assertions if `FresherProjectReponse` has fields
    }

    @Test
    public void removeFreshersFromProject_ReturnUpdatedFresherProject() throws Exception {
        Long projectId = 1L;
        given(projectService.removeFreshersFromProject(ArgumentMatchers.eq(projectId), ArgumentMatchers.any()))
                .willReturn(fresherProjectReponse);

        ResultActions response = mockMvc.perform(delete("/project/{id}/freshers", projectId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fresherProjectRequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").exists());
        // Add more specific assertions if `FresherProjectReponse` has fields
    }
}