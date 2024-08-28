package com.example.FresherManage.Dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor


public class FresherProjectRequest {
    private List<Long> fresher_id;

}
