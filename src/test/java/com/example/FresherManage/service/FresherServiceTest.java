package com.example.FresherManage.service;


import com.example.FresherManage.Dto.request.FresherRequest;
import com.example.FresherManage.Dto.response.CenterResponse;
import com.example.FresherManage.Dto.response.FresherReponse;
import com.example.FresherManage.Dto.response.ProjectResponse;
import com.example.FresherManage.Mapper.FresherMapper;
import com.example.FresherManage.Mapper.ProjectMapping;
import com.example.FresherManage.Service.impl.FresherServiceImpl;
import com.example.FresherManage.domain.Entity.Center;
import com.example.FresherManage.domain.Entity.Fresher;
import com.example.FresherManage.domain.Entity.Project;
import com.example.FresherManage.domain.Exception.AppException;
import com.example.FresherManage.domain.Exception.ErrorCode;
import com.example.FresherManage.repository.CenterRepository;
import com.example.FresherManage.repository.FresherRepository;
import com.example.FresherManage.repository.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FresherServiceTest {

    @Mock
    private FresherRepository fresherRepository;
    @Mock
    private CenterRepository centerRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private FresherMapper fresherMapper;
    @Mock
    private ProjectMapping projectMapping;

    @InjectMocks
    private FresherServiceImpl fresherService;
    private Fresher fresher;
    private Center center;
    private FresherRequest fresherRequest;
    private FresherReponse fresherResponse;

    @BeforeEach
    public void setUp() {
        center = Center.builder().name("Center Name").build();

        fresher = Fresher.builder().name("Test Name").email("test@example.com").programmingLanguage("Java").center(center).build();

        fresherRequest = FresherRequest.builder()
                .email("test@example.com")
                .name("Test Name")
                .center("Center Name")
                .programmingLanguage("Java")
                .build();

        fresherResponse = FresherReponse.builder()
                .name("Test Name")
                .email("test@example.com")
                .center(CenterResponse.builder().name("Center Name").build())
                .programmingLanguage("Java")
                .build();
    }

    @Test
    public void createFresher_ValidRequest_ReturnsFresherResponse() {

        when(fresherRepository.existsByEmail(fresherRequest.getEmail())).thenReturn(false);
        when(centerRepository.findByName(fresherRequest.getCenter())).thenReturn(Optional.of(center));
        when(fresherMapper.toFresher(fresherRequest)).thenReturn(fresher);
        when(fresherRepository.save(fresher)).thenReturn(fresher);
        when(fresherMapper.toFresherReponse(fresher)).thenReturn(fresherResponse);


        FresherReponse response = fresherService.createFresher(fresherRequest);

        Assertions.assertNotNull(response);
        assertEquals(fresherResponse.getEmail(), response.getEmail());
    }

    @Test
    public void getFreshers_ReturnsListOfFresherResponses() {
        List<Fresher> freshers = Arrays.asList(new Fresher(), new Fresher());
        when(fresherRepository.findAll()).thenReturn(freshers);
        when(fresherMapper.toFresherReponse(Mockito.any())).thenReturn(fresherResponse);

        List<FresherReponse> responseList = fresherService.getFreshers();

        Assertions.assertNotNull(responseList);
        assertEquals(freshers.size(), responseList.size());
    }


    @Test
    public void updateFresher_ReturnFresherResponse(){
        when(fresherRepository.findById(fresher.getId())).thenReturn(Optional.of(fresher));
        when(centerRepository.findByName(fresherRequest.getCenter())).thenReturn(Optional.of(new Center()));
        when(fresherMapper.toFresherReponse(fresher)).thenReturn(fresherResponse);

        FresherReponse result = fresherService.updateFresher(fresher.getId(), fresherRequest);

        assertNotNull(result);
        verify(fresherRepository).save(fresher);
        verify(fresherRepository).findById(fresher.getId());

    }
    @Test
    void testDeleteFresher() {
        Long fresherId = 1L;

        doNothing().when(fresherRepository).deleteById(fresherId);

        fresherService.deleteFresher(fresherId);

        verify(fresherRepository).deleteById(fresherId);
    }

    @Test
    public void getProjectsForFresher_ValidFresherId_ReturnsProjectResponses() {
        Long fresherId = 1L;
        List<Project> projects = Arrays.asList(new Project(), new Project());
        when(projectRepository.findByFreshers_Id(fresherId)).thenReturn(projects);
        when(projectMapping.toProjectResponse(Mockito.any())).thenReturn(new ProjectResponse());

        List<ProjectResponse> responseList = fresherService.getProjectsForFresher(fresherId);

        Assertions.assertNotNull(responseList);
        assertEquals(projects.size(), responseList.size());
    }
    @Test
    void testUpdateFresher_NotFound() {
        Long fresherId = 1L;
        FresherRequest fresherRequest = new FresherRequest();

        when(fresherRepository.findById(fresherId)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> fresherService.updateFresher(fresherId, fresherRequest));
        assertEquals(ErrorCode.FRESHER_NOT_EXIST, exception.getErrorCode());
    }

    @Test
    public void findByName_ValidName_ReturnsListOfFresherResponses() {
        String name = "Test Name";
        List<Fresher> freshers = Arrays.asList(new Fresher(), new Fresher());
        when(fresherRepository.findByName(name)).thenReturn(freshers);
        when(fresherMapper.toFresherReponse(Mockito.any())).thenReturn(fresherResponse);

        List<FresherReponse> responseList = fresherService.findByName(name);

        Assertions.assertNotNull(responseList);
        assertEquals(freshers.size(), responseList.size());
    }

    @Test
    public void findByEmail_ValidEmail_ReturnsListOfFresherResponses() {
        String email = "test@example.com";
        List<Fresher> freshers = Arrays.asList(new Fresher(), new Fresher());
        when(fresherRepository.findByEmail(email)).thenReturn(freshers);
        when(fresherMapper.toFresherReponse(Mockito.any())).thenReturn(fresherResponse);

        List<FresherReponse> responseList = fresherService.findByEmail(email);

        Assertions.assertNotNull(responseList);
        assertEquals(freshers.size(), responseList.size());
    }

    @Test
    public void findByProgrammingLanguage_ValidLanguage_ReturnsListOfFresherResponses() {
        String language = "Java";
        List<Fresher> freshers = Arrays.asList(new Fresher(), new Fresher());
        when(fresherRepository.findByProgrammingLanguage(language)).thenReturn(freshers);
        when(fresherMapper.toFresherReponse(Mockito.any())).thenReturn(fresherResponse);

        List<FresherReponse> responseList = fresherService.findByProgrammingLanguage(language);

        Assertions.assertNotNull(responseList);
        assertEquals(freshers.size(), responseList.size());
    }

    @Test
    public void createFresher_EmailExists_ThrowsAppException() {
        when(fresherRepository.existsByEmail(fresherRequest.getEmail())).thenReturn(true);

        AppException exception = assertThrows(AppException.class, () -> {
            fresherService.createFresher(fresherRequest);
        });

        assertEquals(ErrorCode.EMAIL_EXIST, exception.getErrorCode());
    }

}