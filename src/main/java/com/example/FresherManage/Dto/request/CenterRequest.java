package com.example.FresherManage.Dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CenterRequest {
    private String name;

    private String location;
}
