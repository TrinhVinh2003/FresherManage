package com.example.test1.Service;

import com.example.test1.Dto.request.AssignmentRequest;
import com.example.test1.Dto.response.AssignmentResponse;

public interface AssigmentService {
    AssignmentResponse createAssignment(AssignmentRequest assignmentRequest);
}
