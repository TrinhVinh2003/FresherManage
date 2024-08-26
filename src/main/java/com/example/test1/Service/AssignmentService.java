package com.example.test1.Service;

import org.springframework.stereotype.Service;

import com.example.test1.Dto.request.AssignmentRequest;
import com.example.test1.Dto.response.AssignmentResponse;
import com.example.test1.Entity.Assignment;
import com.example.test1.Exception.AppException;
import com.example.test1.Exception.ErrorCode;
import com.example.test1.Mapper.AssignmentMapper;
import com.example.test1.repository.AssignmentRepository;
import com.example.test1.repository.FresherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentMapper assignmentMapper;
    private final AssignmentRepository assignmentRepository;
    private final FresherRepository fresherRepository;

    public AssignmentResponse createAssignment(AssignmentRequest assignmentRequest) {
        var fresher = fresherRepository
                .findById(assignmentRequest.getFresher_id())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));

        Assignment assignment = Assignment.builder()
                .score1(assignmentRequest.getScore1())
                .score2(assignmentRequest.getScore2())
                .score3(assignmentRequest.getScore3())
                .fresher(fresher)
                .build();

        return assignmentMapper.toAssignmentResponse(assignmentRepository.save(assignment));
    }
}
