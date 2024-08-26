package com.example.test1.Mapper;

import com.example.test1.Dto.request.ProjectCreateRequest;
import com.example.test1.Dto.response.FresherProjectReponse;
import com.example.test1.Dto.response.ProjectResponse;
import com.example.test1.Entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapping {
    @Mapping(target = "center",ignore = true)
    Project toProject(ProjectCreateRequest request);


    ProjectResponse toProjectResponse(Project project);

 
    FresherProjectReponse toProjectFresher(Project project);
}
