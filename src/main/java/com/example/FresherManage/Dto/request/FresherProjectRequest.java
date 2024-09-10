package com.example.FresherManage.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class FresherProjectRequest {
    private List<Long> fresher_id;

}
