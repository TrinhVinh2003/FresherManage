package com.example.FresherManage.Service;

import com.example.FresherManage.Dto.request.FresherRequest;
import com.example.FresherManage.Dto.response.FresherReponse;
import com.example.FresherManage.Dto.response.ProjectResponse;

import java.util.List;

public interface FresherService {
    FresherReponse createFresher(FresherRequest fresherRequest);

    List<FresherReponse> getFreshers();

    void deleteFresher(Long id);

    FresherReponse updateFresher(Long id , FresherRequest fresherRequest);

    List<ProjectResponse> getProjectsForFresher(Long fresherId);

    List<FresherReponse> findByName(String name);

    List<FresherReponse> findByEmail(String email);

    List<FresherReponse> findByProgrammingLanguage(String languageprogramming);

}
