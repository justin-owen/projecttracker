package com.groupsix.projecttracker.repository;

import com.groupsix.projecttracker.entity.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RequirementRepository extends JpaRepository<Requirement, UUID> {

    List<Requirement> findAllByProjectId(UUID userId);
}
