package com.example.test1.Dto.response;

import com.example.test1.Entity.Fresher;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CenterResponse {
    private String name; // Đảm bảo rằng tên trường khóa chính là "center_name"

    private String location;
    private Set<FresherReponse> fresherReponses;
}
