package com.example.test1.Service;

import com.example.test1.Dto.response.FresherScoreCountReponse;

import java.util.List;

public interface DashboardService {
    List<FresherScoreCountReponse> evaluateScores();
}
