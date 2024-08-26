package com.example.test1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "center_id", nullable = false, referencedColumnName = "id")
    private Center center;

    private String manager;

    private LocalDate startDate;

    private LocalDate endDate;

    private String language;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    public enum ProjectStatus {
        NOT_STARTED,
        ONGOING,
        CANCELED,
        CLOSED
    }
    @ManyToMany
    private Set<Fresher> freshers;


}