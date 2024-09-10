package com.example.FresherManage.Controller;

import com.example.FresherManage.Service.AssigmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/fresher/{id}")
    ApiResponse<AssignmentResponse> updateAssignment(@PathVariable("id") Long id , @RequestBody AssignmentRequest assignmentRequest){
        return ApiResponse.<AssignmentResponse>builder()
                .result(assignmentService.updateScore(id,assignmentRequest))
                .build();

    }
    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteAssignment(@PathVariable("id") Long id){
        assignmentService.deleteAssignment(id);
        return ApiResponse.<Void>builder()
                .build();
    }
}
