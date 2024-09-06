package com.example.FresherManage.Service;

import com.example.FresherManage.Dto.request.CenterRequest;
import com.example.FresherManage.Dto.response.CenterResponse;

import java.util.List;


public interface CenterService {
    CenterResponse createCenter(CenterRequest request);

    List<CenterResponse> getAll();

    void deleteCenter(Long id);
}
