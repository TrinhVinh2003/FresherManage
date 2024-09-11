package com.example.FresherManage.Service;

import java.util.List;

import com.example.FresherManage.Dto.request.CenterRequest;
import com.example.FresherManage.Dto.response.CenterResponse;

public interface CenterService {
    CenterResponse createCenter(CenterRequest request);

    List<CenterResponse> getAll();

    void deleteCenter(Long id);

    CenterResponse updateCenter(Long id, CenterRequest centerRequest);
}
