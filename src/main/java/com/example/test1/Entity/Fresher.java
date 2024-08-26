package com.example.test1.Entity;

import jakarta.persistence.*;

import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Fresher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String programmingLanguage;

    @ManyToOne
    @JoinColumn(name = "center_id", nullable = false, referencedColumnName = "id")
    private Center center;

    @OneToOne(mappedBy = "fresher", cascade = CascadeType.ALL)
    private Assignment assignment;


}
