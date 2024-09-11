package com.example.FresherManage.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.FresherManage.Dto.response.FresherScoreCountReponse;
import com.example.FresherManage.Service.impl.DashboardServiceImpl;
import com.example.FresherManage.repository.AssignmentRepository;
import com.example.FresherManage.repository.FresherRepository;

@ExtendWith(MockitoExtension.class)
public class DashBoardServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private FresherRepository fresherRepository;

    @InjectMocks
    private DashboardServiceImpl dashboardService;

    @BeforeEach
    void setUp() {
        // Setup code if needed
    }

    @Test
    void countFreshersByCenter_ReturnsCorrectData() {
        // Prepare test data
        List<Object[]> result = List.of(new Object[] {"Center1", 10L}, new Object[] {"Center2", 20L});

        // Mock repository method
        when(fresherRepository.countFreshersByCenter()).thenReturn(result);

        // Call the service method
        Map<String, Integer> resultMap = dashboardService.countFreshersByCenter();

        // Validate the results
        assertNotNull(resultMap);
        assertEquals(2, resultMap.size());
        assertEquals(10, resultMap.get("Center1"));
        assertEquals(20, resultMap.get("Center2"));
    }

    @Test
    void getScoreStatistics_ReturnsCorrectData() {
        // Prepare test data
        List<Object[]> score1Counts = List.of(new Object[] {1.0f, 5L}, new Object[] {4.0f, 10L});
        List<Object[]> score2Counts = List.of(new Object[] {2.0f, 8L}, new Object[] {5.0f, 12L});
        List<Object[]> score3Counts = List.of(new Object[] {3.0f, 7L}, new Object[] {6.0f, 15L});
        List<Object[]> averageCounts = List.of(new Object[] {2.5f, 6L}, new Object[] {5.5f, 14L});

        // Mock repository methods
        when(assignmentRepository.countByScore1()).thenReturn(score1Counts);
        when(assignmentRepository.countByScore2()).thenReturn(score2Counts);
        when(assignmentRepository.countByScore3()).thenReturn(score3Counts);
        when(assignmentRepository.countByAverage()).thenReturn(averageCounts);

        // Call the service method
        List<FresherScoreCountReponse> result = dashboardService.evaluateScores();

        // Validate the results
        assertNotNull(result);
        assertEquals(4, result.size());

        FresherScoreCountReponse score1Response = result.get(0);
        assertEquals("Score1", score1Response.getScoretype());
        assertEquals(Map.of("1-3", 5, "4-6", 10, "7-10", 0, "Unknown", 0), score1Response.getScoreRangeCounts());

        FresherScoreCountReponse score2Response = result.get(1);
        assertEquals("Score2", score2Response.getScoretype());
        assertEquals(Map.of("1-3", 8, "4-6", 12, "7-10", 0, "Unknown", 0), score2Response.getScoreRangeCounts());

        FresherScoreCountReponse score3Response = result.get(2);
        assertEquals("Score3", score3Response.getScoretype());
        assertEquals(Map.of("1-3", 7, "4-6", 15, "7-10", 0, "Unknown", 0), score3Response.getScoreRangeCounts());

        FresherScoreCountReponse averageResponse = result.get(3);
        assertEquals("Average", averageResponse.getScoretype());
        assertEquals(Map.of("1-3", 6, "4-6", 14, "7-10", 0, "Unknown", 0), averageResponse.getScoreRangeCounts());
    }
}
