package com.groupsix.projecttracker.service;

import com.groupsix.projecttracker.dto.RequirementDto;
import com.groupsix.projecttracker.entity.Requirement;
import com.groupsix.projecttracker.mapper.RequirementMapper;
import com.groupsix.projecttracker.repository.ProjectRepository;
import com.groupsix.projecttracker.repository.RequirementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RequirementService {

    private final ProjectRepository projectRepository;
    private final RequirementRepository requirementRepository;
    private final RequirementMapper requirementMapper;

    public List<RequirementDto> listAllRequirementsByProject(UUID projectId){
        return requirementRepository.findAllByProjectId(projectId)
                .stream()
                .map(requirementMapper::toDto)
                .toList();
    }
}
