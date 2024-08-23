package com.example.test1.Service;


import com.example.test1.Dto.request.AssignmentRequest;
import com.example.test1.Dto.response.AssignmentResponse;
import com.example.test1.Entity.Assignment;
import com.example.test1.Mapper.AssignmentMapper;
import com.example.test1.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private AssignmentMapper assignmentMapper;
    private AssignmentRepository assignmentRepository;
    public AssignmentResponse createAssignment(AssignmentRequest assignmentRequest){
        Assignment assignment = assignmentMapper.toAssignment(assignmentRequest);
        return assignmentMapper.toAssignmentResponse(assignmentRepository.save(assignment));

    }

}
