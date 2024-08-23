package com.example.test1.Mapper;

import com.example.test1.Dto.request.FresherRequest;
import com.example.test1.Dto.response.FresherReponse;
import com.example.test1.Entity.Fresher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FresherMapper {

    FresherReponse toFresherReponse(Fresher Fresher);
    @Mapping(target = "center",ignore = true)
    Fresher toFresher(FresherRequest request);
    @Mapping(target = "center",ignore = true)
    void updateFresher(@MappingTarget Fresher fresher, FresherRequest fresherRequest);
}
