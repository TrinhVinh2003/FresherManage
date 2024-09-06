package com.example.FresherManage.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.FresherManage.Dto.request.AssignmentRequest;
import com.example.FresherManage.Dto.response.AssignmentResponse;
import com.example.FresherManage.domain.Entity.Assignment;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {
    AssignmentResponse toAssignmentResponse(Assignment assignment);

    @Mapping(target = "result", ignore = true)
    Assignment toAssignment(AssignmentRequest assignmentRequest);
}
