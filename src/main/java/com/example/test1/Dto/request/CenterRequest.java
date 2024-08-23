package com.example.test1.Dto.request;

import com.example.test1.Entity.Fresher;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CenterRequest {
    private String name; // Đảm bảo rằng tên trường khóa chính là "center_name"


    private String location;




}
