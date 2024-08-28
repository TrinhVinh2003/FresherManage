package com.example.FresherManage.Service;

import com.example.FresherManage.Dto.request.AssignmentRequest;
import com.example.FresherManage.Dto.response.AssignmentResponse;

public interface AssigmentService {
    AssignmentResponse createAssignment(AssignmentRequest assignmentRequest);
}
