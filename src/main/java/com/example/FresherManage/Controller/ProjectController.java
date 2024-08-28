package com.example.FresherManage.Controller;

import com.example.FresherManage.Dto.request.ApiResponse;
import com.example.FresherManage.Dto.request.FresherProjectRequest;
import com.example.FresherManage.Dto.request.ProjectCreateRequest;
import com.example.FresherManage.Dto.response.FresherProjectReponse;
import com.example.FresherManage.Dto.response.ProjectResponse;
import com.example.FresherManage.Service.impl.ProjectServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectServiceImpl projectService;

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

    // Thêm fresher vào project
    @PutMapping("/addFresher/{id}")
    ApiResponse<FresherProjectReponse> addFresher(@PathVariable("id") Long id,@RequestBody FresherProjectRequest request){
        var fresherPro  = projectService.addFresherToProject(id,request);
        return  ApiResponse.<FresherProjectReponse>builder()
                .result(fresherPro)
                .build();
    }

    @DeleteMapping("/{id}/freshers")
    public ApiResponse<FresherProjectReponse> removeFreshersFromProject(@PathVariable Long id, @RequestBody FresherProjectRequest fresherProjectRequest) {
        FresherProjectReponse fresherProjectReponse = projectService.removeFreshersFromProject(id, fresherProjectRequest);
        return ApiResponse.<FresherProjectReponse>builder()
                .result(fresherProjectReponse)
                .build();
    }
}
