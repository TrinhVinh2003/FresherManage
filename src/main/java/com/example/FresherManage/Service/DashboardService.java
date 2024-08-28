package com.example.FresherManage.Service;

import com.example.FresherManage.Dto.response.FresherScoreCountReponse;

import java.util.List;

public interface DashboardService {
    List<FresherScoreCountReponse> evaluateScores();
}
