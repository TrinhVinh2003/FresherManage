package com.example.FresherManage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FresherManage.domain.Entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByFreshers_Id(Long fresherId);
}
