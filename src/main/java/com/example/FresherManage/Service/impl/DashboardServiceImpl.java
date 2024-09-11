package com.example.FresherManage.Service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.FresherManage.Dto.response.FresherScoreCountReponse;
import com.example.FresherManage.Service.DashboardService;
import com.example.FresherManage.repository.AssignmentRepository;
import com.example.FresherManage.repository.FresherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final AssignmentRepository assignmentRepository;

    private final FresherRepository fresherRepository;

    // Thống kê số lượng fresher theo từng trung tâm
    public Map<String, Integer> countFreshersByCenter() {
        List<Object[]> result = fresherRepository.countFreshersByCenter();
        return result.stream()
                .collect(Collectors.toMap(
                        item -> (String) item[0], // Tên trung tâm
                        item -> ((Long) item[1]).intValue() // Số lượng fresher
                        ));
    }

    // số lượng sinh viên trong 3 score với  các  khung điểm 1-3,4-6,7-10

    public List<FresherScoreCountReponse> evaluateScores() {

        Map<String, Map<String, Integer>> scoreCounts = new HashMap<>();
        scoreCounts.put("Score1", mapScoreCounts(convertToMap(assignmentRepository.countByScore1())));
        scoreCounts.put("Score2", mapScoreCounts(convertToMap(assignmentRepository.countByScore2())));
        scoreCounts.put("Score3", mapScoreCounts(convertToMap(assignmentRepository.countByScore3())));
        scoreCounts.put("Average", mapScoreCounts(convertToMap(assignmentRepository.countByAverage())));

        return List.of(
                new FresherScoreCountReponse("Score1", scoreCounts.get("Score1")),
                new FresherScoreCountReponse("Score2", scoreCounts.get("Score2")),
                new FresherScoreCountReponse("Score3", scoreCounts.get("Score3")),
                new FresherScoreCountReponse("Average", scoreCounts.get("Average")));
    }

    // Convert List to Map
    private Map<Float, Long> convertToMap(List<Object[]> list) {
        return list.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        item -> (Float) item[0], // score
                        item -> (Long) item[1] // Count freshers
                        ));
    }

    // Gán số lượng fresher vào các khung điểm phù hợp
    private Map<String, Integer> mapScoreCounts(Map<Float, Long> rawCounts) {
        Map<String, Integer> mappedCounts = new HashMap<>();
        mappedCounts.put("1-3", 0);
        mappedCounts.put("4-6", 0);
        mappedCounts.put("7-10", 0);
        mappedCounts.put("Unknown", 0);

        for (Map.Entry<Float, Long> entry : rawCounts.entrySet()) {
            Float score = entry.getKey();
            Long count = entry.getValue();

            if (score >= 1 && score <= 3) {
                mappedCounts.put("1-3", mappedCounts.get("1-3") + count.intValue());
            } else if (score >= 4 && score <= 6) {
                mappedCounts.put("4-6", mappedCounts.get("4-6") + count.intValue());
            } else if (score >= 7 && score <= 10) {
                mappedCounts.put("7-10", mappedCounts.get("7-10") + count.intValue());
            } else {
                mappedCounts.put("Unknown", mappedCounts.get("Unknown") + count.intValue());
            }
        }

        return mappedCounts;
    }
}
