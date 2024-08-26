package com.example.test1.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.test1.Dto.request.AssignmentRequest;
import com.example.test1.Dto.response.AssignmentResponse;
import com.example.test1.Entity.Assignment;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {
    AssignmentResponse toAssignmentResponse(Assignment assignment);

    @Mapping(target = "result", ignore = true)
    Assignment toAssignment(AssignmentRequest assignmentRequest);
}
