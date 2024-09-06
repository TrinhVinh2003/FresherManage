package com.example.FresherManage.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.example.FresherManage.Dto.response.ProjectResponse;
import com.example.FresherManage.Mapper.ProjectMapping;
import com.example.FresherManage.Service.FresherService;
import com.example.FresherManage.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import com.example.FresherManage.Dto.request.FresherRequest;
import com.example.FresherManage.Dto.response.FresherReponse;
import com.example.FresherManage.domain.Exception.AppException;
import com.example.FresherManage.domain.Exception.ErrorCode;
import com.example.FresherManage.Mapper.FresherMapper;
import com.example.FresherManage.repository.CenterRepository;
import com.example.FresherManage.repository.FresherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FresherServiceImpl implements FresherService{
    private final FresherRepository fresherRepository;

    private final FresherMapper fresherMapper;

    private final CenterRepository centerRepository;

    private final ProjectRepository projectRepository;

    private final ProjectMapping projectMapping;

    // create fresher
    public FresherReponse createFresher(FresherRequest fresherRequest) {

        if (fresherRepository.existsByEmail(fresherRequest.getEmail())) throw new AppException(ErrorCode.EMAIL_EXIST);

        var fresher = fresherMapper.toFresher(fresherRequest);
        var center = centerRepository
                .findByName(fresherRequest.getCenter())
                .orElseThrow(() -> new AppException(ErrorCode.CENTER_NOT_EXIST));
        fresher.setCenter(center);
        return fresherMapper.toFresherReponse(fresherRepository.save(fresher));
    }

    // danh sách các freshers
    public List<FresherReponse> getFreshers() {
        return fresherRepository.findAll().stream()
                .map(fresherMapper::toFresherReponse)
                .toList();
    }


    // danh sách project của  fresher
    public List<ProjectResponse> getProjectsForFresher(Long fresherId) {
        var projects = projectRepository.findByFreshers_Id(fresherId);
        return projects.stream()
                .map(projectMapping::toProjectResponse)
                .collect(Collectors.toList());
    }


    public  List<FresherReponse> findByName(String name){
        var freshers = fresherRepository.findByName(name)
                .stream()
                .map(fresherMapper::toFresherReponse)
                .toList();
        return freshers;
    }

    @Override
    public List<FresherReponse> findByEmail(String email) {
        var freshers = fresherRepository.findByEmail(email)
                .stream().map(fresherMapper::toFresherReponse)
                .toList();

        return freshers;
    }

    @Override
    public List<FresherReponse> findByProgrammingLanguage(String languageprogramming) {
        var freshers = fresherRepository.findByProgrammingLanguage(languageprogramming)
                .stream().map(fresherMapper::toFresherReponse)
                .toList();

        return freshers;
    }

}
