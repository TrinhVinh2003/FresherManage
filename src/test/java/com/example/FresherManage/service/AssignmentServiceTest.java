package com.example.FresherManage.service;

import com.example.FresherManage.Dto.request.AssignmentRequest;
import com.example.FresherManage.Dto.response.AssignmentResponse;
import com.example.FresherManage.Mapper.AssignmentMapper;
import com.example.FresherManage.Service.impl.AssignmentServiceImpl;
import com.example.FresherManage.domain.Entity.Assignment;
import com.example.FresherManage.domain.Entity.Fresher;
import com.example.FresherManage.domain.Exception.AppException;
import com.example.FresherManage.domain.Exception.ErrorCode;
import com.example.FresherManage.repository.AssignmentRepository;
import com.example.FresherManage.repository.FresherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private FresherRepository fresherRepository;

    @Mock
    private AssignmentMapper assignmentMapper;

    @InjectMocks
    private AssignmentServiceImpl assignmentService;

    private AssignmentRequest assignmentRequest;
    private Assignment assignment;
    private Fresher fresher;

    @BeforeEach
    void setUp() {
        fresher = Fresher.builder()
                .id(1L)
                .name("John Doe")
                .email("johndoe@example.com")
                .build();

        assignmentRequest = AssignmentRequest.builder()
                .score1(8.0f)
                .score2(9.0f)
                .score3(7.0f)
                .fresher_id(1L)
                .build();

        assignment = Assignment.builder()
                .score1(8.0f)
                .score2(9.0f)
                .score3(7.0f)
                .fresher(fresher)
                .build();
    }

    @Test
    void createAssignment_ValidRequest_ReturnsAssignmentResponse() {
        // Giả lập fresher tồn tại trong database
        when(fresherRepository.findById(assignmentRequest.getFresher_id())).thenReturn(Optional.of(fresher));
        when(assignmentRepository.save(any(Assignment.class))).thenReturn(assignment);

        AssignmentResponse assignmentResponse = AssignmentResponse.builder()
                .score1(assignment.getScore1())
                .score2(assignment.getScore2())
                .score3(assignment.getScore3())
                .fresher(fresher)
                .build();

        when(assignmentMapper.toAssignmentResponse(any(Assignment.class))).thenReturn(assignmentResponse);

        // Gọi service
        AssignmentResponse result = assignmentService.createAssignment(assignmentRequest);

        // Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(assignment.getScore1(), result.getScore1());
        assertEquals(assignment.getScore2(), result.getScore2());
        assertEquals(assignment.getScore3(), result.getScore3());
        assertEquals(assignment.getFresher(), result.getFresher());
    }

    @Test
    void createAssignment_FresherNotFound_ThrowsAppException() {
        // Giả lập fresher không tồn tại
        when(fresherRepository.findById(assignmentRequest.getFresher_id())).thenReturn(Optional.empty());

        // Kiểm tra AppException
        AppException exception = assertThrows(AppException.class, () -> {
            assignmentService.createAssignment(assignmentRequest);
        });

        assertEquals(ErrorCode.SCORE_OF_FRESHER_EXIST, exception.getErrorCode());
    }

    @Test
    void updateScore_ValidRequest_ReturnsAssignmentResponse() {
        Long assignmentId = 1L;

        // Giả lập dữ liệu
        when(fresherRepository.findById(assignmentRequest.getFresher_id())).thenReturn(Optional.of(fresher));
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));

        AssignmentResponse assignmentResponse = AssignmentResponse.builder()
                .score1(assignmentRequest.getScore1())
                .score2(assignmentRequest.getScore2())
                .score3(assignmentRequest.getScore3())
                .fresher(fresher)
                .build();

        when(assignmentMapper.toAssignmentResponse(any(Assignment.class))).thenReturn(assignmentResponse);

        // Gọi service
        AssignmentResponse result = assignmentService.updateScore(assignmentId, assignmentRequest);

        // Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(assignmentRequest.getScore1(), result.getScore1());
        assertEquals(assignmentRequest.getScore2(), result.getScore2());
        assertEquals(assignmentRequest.getScore3(), result.getScore3());
        assertEquals(fresher, result.getFresher());
    }

    @Test
    void updateScore_AssignmentNotFound_ThrowsAppException() {
        Long assignmentId = 1L;

        // Giả lập assignment không tồn tại
        when(fresherRepository.findById(assignmentRequest.getFresher_id())).thenReturn(Optional.of(fresher));
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.empty());

        // Kiểm tra AppException
        AppException exception = assertThrows(AppException.class, () -> {
            assignmentService.updateScore(assignmentId, assignmentRequest);
        });

        assertEquals(ErrorCode.ASSIGNMENT_NOT_EXIST, exception.getErrorCode());
    }

    @Test
    void deleteAssignment_ValidId_DeletesAssignment() {
        Long assignmentId = 1L;

        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));

        assignmentService.deleteAssignment(assignmentId);

        verify(assignmentRepository, times(1)).deleteById(assignmentId);
    }

    @Test
    void deleteAssignment_AssignmentNotFound_ThrowsAppException() {
        Long assignmentId = 1L;

        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> {
            assignmentService.deleteAssignment(assignmentId);
        });

        assertEquals(ErrorCode.ASSIGNMENT_NOT_EXIST, exception.getErrorCode());
    }
}