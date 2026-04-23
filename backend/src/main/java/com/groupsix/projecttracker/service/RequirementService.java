package com.groupsix.projecttracker.service;

import com.groupsix.projecttracker.dto.RequirementDto;
import com.groupsix.projecttracker.dto.RequirementRequestDto;
import com.groupsix.projecttracker.entity.Requirement;
import com.groupsix.projecttracker.mapper.RequirementMapper;
import com.groupsix.projecttracker.repository.ProjectRepository;
import com.groupsix.projecttracker.repository.RequirementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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

    public RequirementDto createRequirement(RequirementRequestDto requirementRequest){
        Requirement requirement = new Requirement();
        requirement.setId(UUID.randomUUID());
        requirement.setRequirement(requirement.getRequirement());
        requirement.setType(requirementRequest.getType());
        try {
            requirement.setProject(projectRepository.findById(requirementRequest.getProjectId()).orElseThrow());
            Requirement savedReq = requirementRepository.save(requirement);
            return requirementMapper.toDto(savedReq);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Creating Requirement Failed.");
        } catch (NoSuchElementException e){
            throw new NoSuchElementException("Finding project failed.");
        }
    }

    public RequirementDto editRequirement(UUID requirementId, RequirementRequestDto requirementRequest){
        try {
            Requirement requirement = requirementRepository.findById(requirementId).orElseThrow();
            requirement.setRequirement(requirementRequest.getRequirement());
            requirement.setType(requirementRequest.getType());
            Requirement savedReq = requirementRepository.save(requirement);
            return requirementMapper.toDto(savedReq);
        } catch (NoSuchElementException e){
            throw new NoSuchElementException("Finding Requirement failed.");
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Failed editing requirement.");
        }
    }

    public void deleteRequirement(UUID requirementId){
        try {
            Requirement requirement = requirementRepository.findById(requirementId).orElseThrow();
            requirementRepository.delete(requirement);
        } catch (NoSuchElementException e){
            throw new NoSuchElementException("Finding Requirement failed.");
        } catch (Exception e){
            throw new RuntimeException("Failed deleting requirement.");
        }
    }
}
