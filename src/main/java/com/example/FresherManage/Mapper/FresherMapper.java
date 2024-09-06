package com.example.FresherManage.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.FresherManage.Dto.request.FresherRequest;
import com.example.FresherManage.Dto.response.FresherReponse;
import com.example.FresherManage.domain.Entity.Fresher;

@Mapper(componentModel = "spring")
public interface FresherMapper {

    FresherReponse toFresherReponse(Fresher Fresher);

    @Mapping(target = "center", ignore = true)
    Fresher toFresher(FresherRequest request);

    @Mapping(target = "center", ignore = true)
    void updateFresher(@MappingTarget Fresher fresher, FresherRequest fresherRequest);
}
