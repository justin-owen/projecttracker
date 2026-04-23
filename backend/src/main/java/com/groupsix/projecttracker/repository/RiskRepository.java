package com.groupsix.projecttracker.repository;

import com.groupsix.projecttracker.entity.Risk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RiskRepository extends JpaRepository<Risk, UUID> {

    List<Risk> findAllByProjectId(UUID projectId);
}
