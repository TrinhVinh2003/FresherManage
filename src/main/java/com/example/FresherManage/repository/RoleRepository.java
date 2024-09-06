package com.example.FresherManage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FresherManage.domain.Entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
