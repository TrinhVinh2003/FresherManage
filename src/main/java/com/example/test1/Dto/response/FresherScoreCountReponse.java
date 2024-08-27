package com.example.test1.Dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class FresherScoreCountReponse {
    private String scoretype;
    private Map<String,Integer> scoreRangeCounts;

}
