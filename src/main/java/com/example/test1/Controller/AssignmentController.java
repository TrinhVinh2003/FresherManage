package com.example.test1.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test1.Dto.request.ApiResponse;
import com.example.test1.Dto.request.AssignmentRequest;
import com.example.test1.Dto.response.AssignmentResponse;
import com.example.test1.Service.AssignmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/assignment")
@RequiredArgsConstructor
public class AssignmentController {
    @Autowired
    private final AssignmentService assignmentService;

    @PostMapping
    ApiResponse<AssignmentResponse> createAssignment(@RequestBody AssignmentRequest assignmentRequest) {
        return ApiResponse.<AssignmentResponse>builder()
                .result(assignmentService.createAssignment(assignmentRequest))
                .build();
    }
}
