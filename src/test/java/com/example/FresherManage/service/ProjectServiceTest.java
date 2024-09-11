package com.example.FresherManage.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.FresherManage.Dto.request.FresherProjectRequest;
import com.example.FresherManage.Dto.request.ProjectCreateRequest;
import com.example.FresherManage.Dto.response.FresherProjectReponse;
import com.example.FresherManage.Dto.response.ProjectResponse;
import com.example.FresherManage.Mapper.ProjectMapping;
import com.example.FresherManage.Service.impl.EmailService;
import com.example.FresherManage.Service.impl.ProjectServiceImpl;
import com.example.FresherManage.domain.Entity.Center;
import com.example.FresherManage.domain.Entity.Fresher;
import com.example.FresherManage.domain.Entity.Project;
import com.example.FresherManage.repository.CenterRepository;
import com.example.FresherManage.repository.FresherRepository;
import com.example.FresherManage.repository.ProjectRepository;

public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapping projectMapping;

    @Mock
    private CenterRepository centerRepository;

    @Mock
    private FresherRepository fresherRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProject() {
        // Arrange
        ProjectCreateRequest request = ProjectCreateRequest.builder()
                .name("Project A")
                .center("Center A")
                .manager("Manager A")
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .language("Java")
                .status(Project.ProjectStatus.ONGOING)
                .build();

        Center center = Center.builder().name("Center A").build(); // Tạo đối tượng Center với tên phù hợp
        Project project = Project.builder()
                .id(1L)
                .name("Project A")
                .center(center)
                .manager("Manager A")
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .language("Java")
                .status(Project.ProjectStatus.ONGOING)
                .build();

        // Mock các phương thức cần thiết
        when(centerRepository.findByName(request.getCenter())).thenReturn(Optional.of(center));
        when(projectMapping.toProject(request)).thenReturn(project);
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(projectMapping.toProjectResponse(project)).thenReturn(new ProjectResponse());

        // Act
        ProjectResponse response = projectService.createProject(request);

        // Assert
        assertNotNull(response);
        verify(centerRepository, times(1)).findByName(request.getCenter());
        verify(projectRepository, times(1)).save(any(Project.class));
        verify(projectMapping, times(1)).toProjectResponse(project);
    }

    @Test
    void testUpdateProject() {
        // Arrange
        Long projectId = 1L;
        ProjectCreateRequest request = ProjectCreateRequest.builder()
                .name("Updated Project")
                .center("Vinh Moi") // Updated center name
                .manager("Updated Manager")
                .startDate(LocalDate.of(2024, 2, 1))
                .endDate(LocalDate.of(2024, 11, 30))
                .language("Python")
                .status(Project.ProjectStatus.CLOSED)
                .build();

        // Existing project with old center
        Center oldCenter = Center.builder().name("Old Center").build();
        Project existingProject = Project.builder()
                .id(projectId)
                .name("Old Project")
                .center(oldCenter)
                .manager("Old Manager")
                .startDate(LocalDate.of(2023, 1, 1))
                .endDate(LocalDate.of(2023, 12, 31))
                .language("Java")
                .status(Project.ProjectStatus.NOT_STARTED)
                .build();

        // New center to be updated
        Center updatedCenter = Center.builder().name("Vinh Moi").build();
        Project updatedProject = Project.builder()
                .id(projectId)
                .name("Updated Project")
                .center(updatedCenter)
                .manager("Updated Manager")
                .startDate(LocalDate.of(2024, 2, 1))
                .endDate(LocalDate.of(2024, 11, 30))
                .language("Python")
                .status(Project.ProjectStatus.CLOSED)
                .build();

        // Mock necessary methods
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProject));
        when(centerRepository.findByName(request.getCenter())).thenReturn(Optional.of(updatedCenter));
        when(projectMapping.toProject(request)).thenReturn(updatedProject);
        when(projectRepository.save(any(Project.class))).thenReturn(updatedProject);
        when(projectMapping.toProjectResponse(updatedProject))
                .thenReturn(ProjectResponse.builder()
                        .name("Updated Project")
                        .center(updatedCenter)
                        .manager("Updated Manager")
                        .startDate(LocalDate.of(2024, 2, 1))
                        .endDate(LocalDate.of(2024, 11, 30))
                        .language("Python")
                        .status(Project.ProjectStatus.CLOSED)
                        .build());

        // Act
        ProjectResponse response = projectService.updateProject(projectId, request);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals("Updated Project", response.getName());
        assertEquals("Vinh Moi", response.getCenter().getName()); // Check updated center name
        assertEquals("Updated Manager", response.getManager());
        assertEquals(LocalDate.of(2024, 2, 1), response.getStartDate());
        assertEquals(LocalDate.of(2024, 11, 30), response.getEndDate());
        assertEquals("Python", response.getLanguage());
        assertEquals(Project.ProjectStatus.CLOSED, response.getStatus());

        verify(projectRepository, times(1)).findById(projectId);
        verify(centerRepository, times(1)).findByName(request.getCenter());
        verify(projectRepository, times(1)).save(any(Project.class));
        verify(projectMapping, times(1)).toProjectResponse(updatedProject);
    }

    @Test
    void testDeleteProject() {
        // Arrange
        Long projectId = 1L;

        // Act
        projectService.deleteProject(projectId);

        // Assert
        verify(projectRepository, times(1)).deleteById(projectId);
    }

    @Test
    void testGetAllProjects() {
        // Arrange
        Project project = Project.builder()
                .id(1L)
                .name("Project A")
                .center(Center.builder().name("Center A").build())
                .manager("Manager A")
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .language("Java")
                .status(Project.ProjectStatus.ONGOING)
                .build();

        List<Project> projects = List.of(project);
        ProjectResponse response = ProjectResponse.builder()
                .name("Project A")
                .center(Center.builder().name("Center A").build())
                .manager("Manager A")
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .language("Java")
                .status(Project.ProjectStatus.ONGOING)
                .build();

        // Mock các phương thức cần thiết
        when(projectRepository.findAll()).thenReturn(projects);
        when(projectMapping.toProjectResponse(project)).thenReturn(response);

        // Act
        List<ProjectResponse> responses = projectService.getAll();

        // Assert
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Project A", responses.get(0).getName());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void testAddFresherToProject() {
        // Arrange
        Long projectId = 1L;
        FresherProjectRequest request = new FresherProjectRequest(List.of(1L, 2L));
        Fresher fresher1 = Fresher.builder()
                .id(1L)
                .name("Fresher 1")
                .email("fresher1@example.com")
                .build();
        Fresher fresher2 = Fresher.builder()
                .id(2L)
                .name("Fresher 2")
                .email("fresher2@example.com")
                .build();
        Project project = Project.builder()
                .id(projectId)
                .name("Project A")
                .center(Center.builder().name("Center A").build())
                .manager("Manager A")
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .language("Java")
                .status(Project.ProjectStatus.ONGOING)
                .build();

        // Mock các phương thức cần thiết
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(fresherRepository.findAllById(request.getFresher_id())).thenReturn(List.of(fresher1, fresher2));
        when(projectMapping.toProjectFresher(project)).thenReturn(new FresherProjectReponse());

        // Act
        FresherProjectReponse response = projectService.addFresherToProject(projectId, request);

        // Assert
        assertNotNull(response);
        verify(projectRepository, times(1)).findById(projectId);
        verify(fresherRepository, times(1)).findAllById(request.getFresher_id());
        verify(projectRepository, times(1)).save(project);
        verify(emailService, times(2))
                .sendProjectUpdateEmail(any(), any(), any(), any(), any()); // Kiểm tra số lượng email gửi
        verify(projectMapping, times(1)).toProjectFresher(project);
    }

    @Test
    void testRemoveFreshersFromProject() {
        // Arrange
        Long projectId = 1L;
        FresherProjectRequest request = new FresherProjectRequest(List.of(1L, 2L));
        Fresher fresher1 = Fresher.builder()
                .id(1L)
                .name("Fresher 1")
                .email("fresher1@example.com")
                .build();
        Fresher fresher2 = Fresher.builder()
                .id(2L)
                .name("Fresher 2")
                .email("fresher2@example.com")
                .build();
        Project project = Project.builder()
                .id(projectId)
                .name("Project A")
                .center(Center.builder().name("Center A").build())
                .manager("Manager A")
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .language("Java")
                .status(Project.ProjectStatus.ONGOING)
                .freshers(new HashSet<>(List.of(fresher1, fresher2)))
                .build();

        // Mock các phương thức cần thiết
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(fresherRepository.findAllById(request.getFresher_id())).thenReturn(List.of(fresher1, fresher2));
        when(projectMapping.toProjectFresher(project)).thenReturn(new FresherProjectReponse());

        // Act
        FresherProjectReponse response = projectService.removeFreshersFromProject(projectId, request);

        // Assert
        assertNotNull(response);
        verify(projectRepository, times(1)).findById(projectId);
        verify(fresherRepository, times(1)).findAllById(request.getFresher_id());
        verify(projectRepository, times(1)).save(project);
        verify(emailService, times(2))
                .sendProjectUpdateEmail(any(), any(), any(), any(), any()); // Kiểm tra số lượng email gửi
        verify(projectMapping, times(1)).toProjectFresher(project);
    }
}
