package com.example.test1.Service;

import com.example.test1.Dto.request.FresherRequest;
import com.example.test1.Dto.response.FresherReponse;
import com.example.test1.repository.CenterRepository;
import com.example.test1.Entity.Fresher;
import com.example.test1.Exception.AppException;
import com.example.test1.Exception.ErrorCode;
import com.example.test1.Mapper.FresherMapper;
import com.example.test1.repository.FresherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FresherService {
    private  FresherRepository fresherRepository;
    private FresherMapper fresherMapper;
    private  CenterRepository centerRepository;

    public FresherReponse createFresher(FresherRequest fresherRequest){

        var fresher = fresherMapper.toFresher(fresherRequest);
        var center = centerRepository.findByCenter(fresherRequest.getName()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        fresher.setCenter(center);
        return fresherMapper.toFresherReponse(fresherRepository.save(fresher));
    }

    public List<FresherReponse> getFreshers(){
        return fresherRepository.findAll().stream().map(fresherMapper::toFresherReponse).toList();
    }



}
