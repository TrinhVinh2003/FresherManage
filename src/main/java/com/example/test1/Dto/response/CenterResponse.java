package com.example.test1.Dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CenterResponse {
    private String name; // Đảm bảo rằng tên trường khóa chính là "center_name"

    private String location;
}
