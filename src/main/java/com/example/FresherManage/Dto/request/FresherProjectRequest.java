package com.example.FresherManage.Dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class FresherProjectRequest {
    private List<Long> fresher_id;
}
