package com.example.FresherManage.Dto.response;

import java.util.Set;

import com.example.FresherManage.domain.Entity.Fresher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FresherProjectReponse {
    private String name;
    private Set<Fresher> freshers;
}
