package com.example.test1.Service;

import com.example.test1.Dto.request.FresherRequest;
import com.example.test1.Dto.response.FresherReponse;

import java.util.List;

public interface FresherService {
    FresherReponse createFresher(FresherRequest fresherRequest);

    List<FresherReponse> getFreshers();
}
