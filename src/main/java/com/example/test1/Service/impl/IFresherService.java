package com.example.test1.Service.impl;

import java.util.List;

import com.example.test1.Service.FresherService;
import org.springframework.stereotype.Service;

import com.example.test1.Dto.request.FresherRequest;
import com.example.test1.Dto.response.FresherReponse;
import com.example.test1.Exception.AppException;
import com.example.test1.Exception.ErrorCode;
import com.example.test1.Mapper.FresherMapper;
import com.example.test1.repository.CenterRepository;
import com.example.test1.repository.FresherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IFresherService implements FresherService{
    private final FresherRepository fresherRepository;
    private final FresherMapper fresherMapper;
    private final CenterRepository centerRepository;

    public FresherReponse createFresher(FresherRequest fresherRequest) {

        if (fresherRepository.existsByEmail(fresherRequest.getEmail())) throw new AppException(ErrorCode.EMAIL_EXIST);

        var fresher = fresherMapper.toFresher(fresherRequest);
        var center = centerRepository
                .findByName(fresherRequest.getCenter())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        fresher.setCenter(center);
        return fresherMapper.toFresherReponse(fresherRepository.save(fresher));
    }

    public List<FresherReponse> getFreshers() {
        return fresherRepository.findAll().stream()
                .map(fresherMapper::toFresherReponse)
                .toList();
    }
}
