package com.example.FresherManage.Service;

import com.example.FresherManage.Dto.request.FresherProjectRequest;
import com.example.FresherManage.Dto.request.ProjectCreateRequest;
import com.example.FresherManage.Dto.response.FresherProjectReponse;
import com.example.FresherManage.Dto.response.ProjectResponse;

import java.util.List;

public interface ProjectService {
    ProjectResponse createProject(ProjectCreateRequest request);

    ProjectResponse updateProject(Long id, ProjectCreateRequest request);

    List<ProjectResponse> getAll();

    FresherProjectReponse addFresherToProject(Long id, FresherProjectRequest fresherProjectRequest);
}
