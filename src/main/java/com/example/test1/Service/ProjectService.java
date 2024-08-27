package com.example.test1.Service;

import com.example.test1.Dto.request.FresherProjectRequest;
import com.example.test1.Dto.request.ProjectCreateRequest;
import com.example.test1.Dto.response.FresherProjectReponse;
import com.example.test1.Dto.response.ProjectResponse;

import java.util.List;

public interface ProjectService {
    ProjectResponse createProject(ProjectCreateRequest request);

    ProjectResponse updateProject(Long id, ProjectCreateRequest request);

    List<ProjectResponse> getAll();

    FresherProjectReponse addFresherToProject(Long id, FresherProjectRequest fresherProjectRequest);
}
