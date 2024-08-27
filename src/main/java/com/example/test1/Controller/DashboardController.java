package com.example.test1.Controller;


import com.example.test1.Dto.request.ApiResponse;
import com.example.test1.Dto.response.FresherScoreCountReponse;
import com.example.test1.Service.impl.IDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {


    @Autowired
    private final IDashboardService dashboardService;

    @GetMapping("//count-freshers-by-score")
    ApiResponse<List<FresherScoreCountReponse>> getAll(){
        return ApiResponse.<List<FresherScoreCountReponse>>builder()
                .result(dashboardService.evaluateScores())
                .build();
    }

    @GetMapping("/count-freshers-by-center")
    ApiResponse<Map<String, Integer>>  getCountFreshersByCenter(){
        return ApiResponse.<Map<String, Integer>>builder()
                .result(dashboardService.countFreshersByCenter())
                .build();
    }
}
