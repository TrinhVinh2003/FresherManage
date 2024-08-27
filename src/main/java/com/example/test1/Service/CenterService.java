package com.example.test1.Service;

import com.example.test1.Dto.request.CenterRequest;
import com.example.test1.Dto.request.UserCreationRequest;
import com.example.test1.Dto.response.CenterResponse;
import com.example.test1.Dto.response.UserResponse;

import java.util.List;

public interface CenterService {
    CenterResponse createCenter(CenterRequest request);

    List<CenterResponse> getAll();

    void delete(String name);
}
