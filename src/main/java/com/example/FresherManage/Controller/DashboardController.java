package com.example.FresherManage.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FresherManage.Dto.request.ApiResponse;
import com.example.FresherManage.Dto.response.FresherScoreCountReponse;
import com.example.FresherManage.Service.impl.DashboardServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    @Autowired
    private final DashboardServiceImpl dashboardService;

    @GetMapping("//count-freshers-by-score")
    ApiResponse<List<FresherScoreCountReponse>> getAll() {
        return ApiResponse.<List<FresherScoreCountReponse>>builder()
                .result(dashboardService.evaluateScores())
                .build();
    }

    @GetMapping("/count-freshers-by-center")
    ApiResponse<Map<String, Integer>> getCountFreshersByCenter() {
        return ApiResponse.<Map<String, Integer>>builder()
                .result(dashboardService.countFreshersByCenter())
                .build();
    }
}
