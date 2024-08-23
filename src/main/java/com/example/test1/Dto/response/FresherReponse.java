package com.example.test1.Dto.response;

import com.example.test1.Entity.Center;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

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
