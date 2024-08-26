package com.example.test1.Dto.request;

import com.example.test1.Entity.Center;
import com.example.test1.Entity.Project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;


@Getter
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
