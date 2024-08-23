package com.example.test1.Dto.response;

import com.example.test1.Entity.Permission;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class  RoleResponse {
    String name;
    String description;

    Set<PermissionResponse> permissions;
}
