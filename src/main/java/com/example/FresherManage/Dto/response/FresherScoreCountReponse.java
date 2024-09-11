package com.example.FresherManage.Dto.response;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FresherScoreCountReponse {
    private String scoretype;
    private Map<String, Integer> scoreRangeCounts;
}
