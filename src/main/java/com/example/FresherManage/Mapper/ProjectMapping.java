package com.example.FresherManage.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.FresherManage.Dto.request.ProjectCreateRequest;
import com.example.FresherManage.Dto.response.FresherProjectReponse;
import com.example.FresherManage.Dto.response.ProjectResponse;
import com.example.FresherManage.domain.Entity.Project;

@Mapper(componentModel = "spring")
public interface ProjectMapping {
    @Mapping(target = "center", ignore = true)
    Project toProject(ProjectCreateRequest request);

    ProjectResponse toProjectResponse(Project project);

    FresherProjectReponse toProjectFresher(Project project);
}
