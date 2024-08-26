package com.example.test1.Dto.response;

import com.example.test1.Entity.Fresher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FresherProjectReponse {
    private String name;
    private Set<Fresher> freshers;

}
