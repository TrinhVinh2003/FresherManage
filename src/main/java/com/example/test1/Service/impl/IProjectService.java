package com.example.test1.Service.impl;

import com.example.test1.Dto.request.FresherProjectRequest;
import com.example.test1.Dto.request.ProjectCreateRequest;
import com.example.test1.Dto.response.FresherProjectReponse;
import com.example.test1.Dto.response.ProjectResponse;
import com.example.test1.Exception.AppException;
import com.example.test1.Exception.ErrorCode;
import com.example.test1.Mapper.ProjectMapping;
import com.example.test1.Service.ProjectService;
import com.example.test1.repository.CenterRepository;
import com.example.test1.repository.FresherRepository;
import com.example.test1.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IProjectService implements ProjectService {
    private final ProjectRepository projectRepository;

    private  final ProjectMapping projectMapping;

    private  final CenterRepository centerRepository;

    private  final FresherRepository fresherRepository;
    public ProjectResponse createProject(ProjectCreateRequest request){

        var project = projectMapping.toProject(request);

        var center = centerRepository.findByName(request.getCenter()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        project.setCenter(center);
        projectRepository.save(project);
        return projectMapping.toProjectResponse(project);
    }

    public ProjectResponse updateProject(Long id, ProjectCreateRequest request){
        var project = projectRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        project.setName(request.getName());
        project.setManager(request.getManager());
        project.setLanguage(request.getLanguage());
        project.setStatus(request.getStatus());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        var center = centerRepository.findByName(request.getCenter()).orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));
        project.setCenter(center);
        projectRepository.save(project);
        return projectMapping.toProjectResponse(project);
    }

    public void deleteProject(Long id){
        projectRepository.deleteById(id);
    }
    public List<ProjectResponse> getAll(){
        return projectRepository.findAll().stream().map(projectMapping::toProjectResponse).toList();
    }

    public FresherProjectReponse addFresherToProject(Long id,FresherProjectRequest fresherProjectRequest){
        var project = projectRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXIST));
        var freshers = fresherRepository.findAllById(fresherProjectRequest.getFresher_id());


        project.setFreshers(new HashSet<>(freshers));
        projectRepository.save(project);
        return projectMapping.toProjectFresher(project);
    }
}

