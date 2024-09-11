package com.example.FresherManage.Service;

import java.util.List;

import com.example.FresherManage.Dto.response.FresherScoreCountReponse;

public interface DashboardService {
    List<FresherScoreCountReponse> evaluateScores();
}
