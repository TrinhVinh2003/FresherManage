package com.example.test1.Mapper;

import org.mapstruct.Mapper;

import com.example.test1.Dto.request.CenterRequest;
import com.example.test1.Dto.response.CenterResponse;
import com.example.test1.Entity.Center;

@Mapper(componentModel = "spring")
public interface CenterMapper {
    //    @Mapping(target = "freshers",ignore = true)
    Center toCenter(CenterRequest request);

    CenterResponse toCenterReponse(Center center);
}
