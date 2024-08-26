package com.example.test1.Dto.response;

import com.example.test1.Entity.Center;
import com.example.test1.Entity.Project;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
    private String name;

    private Center center;

    private String manager;

    private LocalDate startDate;

    private LocalDate endDate;

    private String language;

    @Enumerated(EnumType.STRING)
    private Project.ProjectStatus status;

    public enum ProjectStatus {
        NOT_STARTED,
        ONGOING,
        CANCELED,
        CLOSED
    }
}
