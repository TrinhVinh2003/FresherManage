package com.example.FresherManage.Controller;

import java.util.List;

import com.example.FresherManage.Dto.response.ProjectResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.FresherManage.Dto.request.ApiResponse;
import com.example.FresherManage.Dto.request.FresherRequest;
import com.example.FresherManage.Dto.response.FresherReponse;
import com.example.FresherManage.Service.impl.FresherServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/fresher")
@RequiredArgsConstructor
public class FresherController {
    private static final Logger log = LoggerFactory.getLogger(FresherController.class);

    @Autowired
    private final FresherServiceImpl fresherService;

    @PostMapping
    ApiResponse<FresherReponse> createFresher(@RequestBody FresherRequest fresherRequest) {
        var fresher = fresherService.createFresher(fresherRequest);

        return ApiResponse.<FresherReponse>builder().result(fresher).build();
    }

    @GetMapping
    ApiResponse<List<FresherReponse>> getAll() {
        return ApiResponse.<List<FresherReponse>>builder()
                .result(fresherService.getFreshers())
                .build();
    }

    @GetMapping("/project/{id}")
    ApiResponse<List<ProjectResponse>> getProjectByFresher(@PathVariable("id") Long id){
        var project = fresherService.getProjectsForFresher(id);
        return ApiResponse.<List<ProjectResponse>>builder()
                .result(project)
                .build();
    }

}
