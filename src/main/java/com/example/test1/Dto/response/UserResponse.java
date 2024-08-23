package com.example.test1.Dto.response;

import com.example.test1.Entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    LocalDate dob;
    Set<RoleResponse> roles;
}
