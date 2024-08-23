package com.example.test1.Dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FresherRequest {

    private String name;

    private String email;

    private String programmingLanguage;
    private String center;
}
