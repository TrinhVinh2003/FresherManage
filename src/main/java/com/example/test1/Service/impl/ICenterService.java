package com.example.test1.Service.impl;

import java.util.List;

import com.example.test1.Service.CenterService;
import org.springframework.stereotype.Service;

import com.example.test1.Dto.request.CenterRequest;
import com.example.test1.Dto.response.CenterResponse;
import com.example.test1.Exception.AppException;
import com.example.test1.Exception.ErrorCode;
import com.example.test1.Mapper.CenterMapper;
import com.example.test1.repository.CenterRepository;
import com.example.test1.repository.FresherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ICenterService implements CenterService {
    private final CenterRepository centerRepository;
    private final CenterMapper centerMapper;
    private final FresherRepository fresherRepository;

    public CenterResponse createCenter(CenterRequest request) {
        if (centerRepository.existsByName(request.getName())) throw new AppException(ErrorCode.CENTER_EXIST);
        var center = centerMapper.toCenter(request);

        return centerMapper.toCenterReponse(centerRepository.save(center));
    }

    public List<CenterResponse> getAll() {

        return centerRepository.findAll().stream()
                .map(centerMapper::toCenterReponse)
                .toList();
    }

    public void delete(String name) {
        centerRepository.deleteById(name);
    }
}
