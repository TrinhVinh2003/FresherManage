package com.example.FresherManage.Dto.response;

import java.time.LocalDate;
import java.util.Set;

import com.example.FresherManage.domain.Entity.Fresher;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailResponse {
    String id;
    String username;
    LocalDate dob;
    Set<RoleResponse> roles;
    Fresher fresher;
}
