    package com.example.FresherManage.Service.impl;

    import com.example.FresherManage.Service.AssigmentService;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.stereotype.Service;

    import com.example.FresherManage.Dto.request.AssignmentRequest;
    import com.example.FresherManage.Dto.response.AssignmentResponse;
    import com.example.FresherManage.domain.Entity.Assignment;
    import com.example.FresherManage.domain.Exception.AppException;
    import com.example.FresherManage.domain.Exception.ErrorCode;
    import com.example.FresherManage.Mapper.AssignmentMapper;
    import com.example.FresherManage.repository.AssignmentRepository;
    import com.example.FresherManage.repository.FresherRepository;

    import lombok.RequiredArgsConstructor;

    @Service
    @RequiredArgsConstructor
    public class AssignmentServiceImpl implements AssigmentService {
        private final AssignmentMapper assignmentMapper;
        private final AssignmentRepository assignmentRepository;
        private final FresherRepository fresherRepository;

        @PreAuthorize("hasRole('ADMIN')")
        public AssignmentResponse createAssignment(AssignmentRequest assignmentRequest) {
            var fresher = fresherRepository
                    .findById(assignmentRequest.getFresher_id())
                    .orElseThrow(() -> new AppException(ErrorCode.SCORE_OF_FRESHER_EXIST));

            Assignment assignment = Assignment.builder()
                    .score1(assignmentRequest.getScore1())
                    .score2(assignmentRequest.getScore2())
                    .score3(assignmentRequest.getScore3())
                    .fresher(fresher)
                    .build();

            return assignmentMapper.toAssignmentResponse(assignmentRepository.save(assignment));
        }
        @PreAuthorize("hasRole('ADMIN')")
        public AssignmentResponse updateScore(Long id,AssignmentRequest request){

            var assignment = assignmentRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ASSIGNMENT_NOT_EXIST));

            assignment.setScore1(request.getScore1());
            assignment.setScore2(request.getScore2());
            assignment.setScore3(request.getScore3());

            assignmentRepository.save(assignment);
            return assignmentMapper.toAssignmentResponse(assignment);

        }
        @PreAuthorize("hasRole('ADMIN')")
        public void deleteAssignment(Long id){
            Assignment assignment = assignmentRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ASSIGNMENT_NOT_EXIST));

            assignmentRepository.deleteById(id);
        }
    }
