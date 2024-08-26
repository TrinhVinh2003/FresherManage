package com.example.test1.Dto.request;

import com.example.test1.Entity.Fresher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor


public class FresherProjectRequest {
    private List<Long> fresher_id;

}
