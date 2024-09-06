package com.example.FresherManage.Dto.response;

import com.example.FresherManage.domain.Entity.Fresher;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignmentResponse {
    private Float score1;
    private Float score2;
    private Float score3;

    private Float result;
    private Fresher fresher;
}
