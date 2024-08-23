package com.example.test1.Mapper;

import com.example.test1.Dto.request.AssignmentRequest;
import com.example.test1.Dto.response.AssignmentResponse;
import com.example.test1.Entity.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {
    AssignmentResponse toAssignmentResponse(Assignment assignment);
//    @Mapping(target = "fresher",ignore = true)
    Assignment toAssignment(AssignmentRequest assignmentRequest);

}
