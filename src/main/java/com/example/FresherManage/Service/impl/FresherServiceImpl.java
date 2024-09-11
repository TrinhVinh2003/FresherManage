package com.example.FresherManage.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.FresherManage.Dto.request.FresherRequest;
import com.example.FresherManage.Dto.response.FresherReponse;
import com.example.FresherManage.Dto.response.ProjectResponse;
import com.example.FresherManage.Mapper.FresherMapper;
import com.example.FresherManage.Mapper.ProjectMapping;
import com.example.FresherManage.Service.FresherService;
import com.example.FresherManage.domain.Entity.Fresher;
import com.example.FresherManage.domain.Exception.AppException;
import com.example.FresherManage.domain.Exception.ErrorCode;
import com.example.FresherManage.repository.CenterRepository;
import com.example.FresherManage.repository.FresherRepository;
import com.example.FresherManage.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FresherServiceImpl implements FresherService {
    private final FresherRepository fresherRepository;

    private final FresherMapper fresherMapper;

    private final CenterRepository centerRepository;

    private final ProjectRepository projectRepository;

    private final ProjectMapping projectMapping;

    // create fresher
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public List<FresherReponse> getFreshers() {
        return fresherRepository.findAll().stream()
                .map(fresherMapper::toFresherReponse)
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteFresher(Long id) {
        fresherRepository.deleteById(id);
    }

    @Override
    @PostAuthorize("returnObject.email == authentication.principal.email")
    public FresherReponse getFresherById(Long id) {
        var fresher = fresherRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.FRESHER_NOT_EXIST));
        return fresherMapper.toFresherReponse(fresher);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public FresherReponse updateFresher(Long id, FresherRequest fresherRequest) {
        Fresher fresher =
                fresherRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.FRESHER_NOT_EXIST));
        fresher.setName(fresherRequest.getName());
        fresher.setEmail(fresherRequest.getEmail());
        fresher.setProgrammingLanguage(fresherRequest.getProgrammingLanguage());
        var center = centerRepository
                .findByName(fresherRequest.getCenter())
                .orElseThrow(() -> new AppException(ErrorCode.CENTER_NOT_EXIST));
        fresher.setCenter(center);
        fresherRepository.save(fresher);

        return fresherMapper.toFresherReponse(fresher);
    }

    // danh sách project của  fresher

    public List<ProjectResponse> getProjectsForFresher(Long fresherId) {
        var projects = projectRepository.findByFreshers_Id(fresherId);
        return projects.stream().map(projectMapping::toProjectResponse).collect(Collectors.toList());
    }

    public List<FresherReponse> searchByName(String name) {
        var freshers = fresherRepository.searchByName(name).stream()
                .map(fresherMapper::toFresherReponse)
                .toList();
        return freshers;
    }

    @Override
    public List<FresherReponse> searchByEmail(String email) {
        var freshers = fresherRepository.searchByEmail(email).stream().map(fresherMapper::toFresherReponse).toList();
        return freshers;
    }



    @Override
    public List<FresherReponse> searchByProgrammingLanguage(String languageprogramming) {
        var freshers = fresherRepository.searchByProgrammingLanguageContaining(languageprogramming).stream()
                .map(fresherMapper::toFresherReponse)
                .toList();

        return freshers;
    }
}
