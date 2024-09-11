package com.example.FresherManage.Service;

import java.util.List;

import com.example.FresherManage.Dto.request.FresherRequest;
import com.example.FresherManage.Dto.response.FresherReponse;
import com.example.FresherManage.Dto.response.ProjectResponse;

public interface FresherService {
    FresherReponse createFresher(FresherRequest fresherRequest);

    List<FresherReponse> getFreshers();

    void deleteFresher(Long id);

    FresherReponse getFresherById(Long id);

    FresherReponse updateFresher(Long id, FresherRequest fresherRequest);

    List<ProjectResponse> getProjectsForFresher(Long fresherId);

    List<FresherReponse> searchByName(String name);

    List<FresherReponse> searchByEmail(String email);

    List<FresherReponse> searchByProgrammingLanguage(String languageprogramming);
}
