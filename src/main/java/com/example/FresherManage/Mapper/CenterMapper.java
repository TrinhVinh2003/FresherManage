package com.example.FresherManage.Mapper;

import org.mapstruct.Mapper;

import com.example.FresherManage.Dto.request.CenterRequest;
import com.example.FresherManage.Dto.response.CenterResponse;
import com.example.FresherManage.Entity.Center;

@Mapper(componentModel = "spring")
public interface CenterMapper {
    //    @Mapping(target = "freshers",ignore = true)
    Center toCenter(CenterRequest request);

    CenterResponse toCenterReponse(Center center);
}
