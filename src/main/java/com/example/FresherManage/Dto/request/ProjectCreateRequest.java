package com.example.FresherManage.Dto.request;

import com.example.FresherManage.domain.Entity.Project;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCreateRequest {
    private String name;
    private String center;

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
