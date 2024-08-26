package com.example.test1.Controller;

import com.example.test1.Dto.request.ApiResponse;
import com.example.test1.Dto.request.FresherProjectRequest;
import com.example.test1.Dto.request.ProjectCreateRequest;
import com.example.test1.Dto.response.FresherProjectReponse;
import com.example.test1.Dto.response.ProjectResponse;
import com.example.test1.Service.ProjectService;
import com.example.test1.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    ApiResponse<ProjectResponse> createProject(@RequestBody ProjectCreateRequest request){
        return ApiResponse.<ProjectResponse>builder()
                .result(projectService.createProject(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<ProjectResponse> updateProject(@PathVariable("id") Long id,@RequestBody ProjectCreateRequest request){
        return ApiResponse.<ProjectResponse>builder()
                .result(projectService.updateProject(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteProject(@PathVariable("id") Long id){
        return ApiResponse.<Void>builder()
                .build();
    }

    @GetMapping
    ApiResponse<List<ProjectResponse>> getAll(){
        return ApiResponse.<List<ProjectResponse>>builder()
                .result(projectService.getAll())
                .build();
    }

    @PutMapping("/addFresher/{id}")
    ApiResponse<FresherProjectReponse> addFresher(@PathVariable("id") Long id,@RequestBody FresherProjectRequest request){
        var fresherPro  = projectService.addFresherToProject(id,request);
        return  ApiResponse.<FresherProjectReponse>builder()
                .result(fresherPro)
                .build();
    }

}
