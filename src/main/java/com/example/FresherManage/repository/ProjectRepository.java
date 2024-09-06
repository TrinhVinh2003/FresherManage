package com.example.FresherManage.repository;

import com.example.FresherManage.domain.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository  extends JpaRepository<Project,Long> {
    List<Project> findByFreshers_Id(Long fresherId);
}
