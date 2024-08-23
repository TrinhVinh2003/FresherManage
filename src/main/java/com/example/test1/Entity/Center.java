package com.example.test1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Center {
    @Id
    @Column(name = "center")
    private String name; // Đảm bảo rằng tên trường khóa chính là "center_name"

    private String location;

    @OneToMany(mappedBy = "center",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Fresher> freshers;
}
