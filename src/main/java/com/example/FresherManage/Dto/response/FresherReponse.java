package com.example.FresherManage.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FresherReponse {
    private String name;

    private String email;

    private String programmingLanguage;
    private CenterResponse center;
}
