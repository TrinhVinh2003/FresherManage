package com.example.FresherManage.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FresherManage.Dto.request.ApiResponse;
import com.example.FresherManage.Dto.request.AssignmentRequest;
import com.example.FresherManage.Dto.response.AssignmentResponse;
import com.example.FresherManage.Service.impl.AssignmentServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/assignment")
@RequiredArgsConstructor
public class AssignmentController {
    @Autowired
    private final AssignmentServiceImpl assignmentService;

    @PostMapping
    ApiResponse<AssignmentResponse> createAssignment(@RequestBody AssignmentRequest assignmentRequest) {
        return ApiResponse.<AssignmentResponse>builder()
                .result(assignmentService.createAssignment(assignmentRequest))
                .build();
    }
}
