package com.groupsix.projecttracker.repository;

import com.groupsix.projecttracker.entity.EffortEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EffortRepository extends JpaRepository<EffortEntity, UUID> {
    List<EffortEntity> findByProjectId(UUID projectId);
}