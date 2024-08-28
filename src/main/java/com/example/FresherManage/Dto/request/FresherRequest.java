package com.example.FresherManage.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
