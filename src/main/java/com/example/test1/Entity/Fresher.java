package com.example.test1.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Fresher {
    @Id
    private Long id;

    private String name;

    private String email;

    private String programmingLanguage; // Đã sửa chính tả từ "programingLanguage" thành "programmingLanguage"

    @ManyToOne
    @JoinColumn(name = "center", nullable = false, referencedColumnName = "center")
    private Center center;
}
