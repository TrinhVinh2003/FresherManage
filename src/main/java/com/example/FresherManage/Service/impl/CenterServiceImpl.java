package com.example.FresherManage.Service.impl;

import java.util.List;

import com.example.FresherManage.Service.CenterService;
import org.springframework.stereotype.Service;

import com.example.FresherManage.Dto.request.CenterRequest;
import com.example.FresherManage.Dto.response.CenterResponse;
import com.example.FresherManage.domain.Exception.AppException;
import com.example.FresherManage.domain.Exception.ErrorCode;
import com.example.FresherManage.Mapper.CenterMapper;
import com.example.FresherManage.repository.CenterRepository;
import com.example.FresherManage.repository.FresherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CenterServiceImpl implements CenterService {
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

    @Override
    public void deleteCenter(Long id) {
        centerRepository.deleteById(id);
    }


}
