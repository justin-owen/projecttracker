package com.groupsix.projecttracker.repository;

import com.groupsix.projecttracker.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
}
