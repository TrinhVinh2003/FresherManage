package com.example.test1.Dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionResponse {

    String name;
    String description;



}

