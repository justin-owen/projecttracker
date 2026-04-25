package com.groupsix.projecttracker.service;

import com.groupsix.projecttracker.dto.EffortDto;
import com.groupsix.projecttracker.entity.EffortEntity;
import com.groupsix.projecttracker.entity.Project;
import com.groupsix.projecttracker.entity.Requirement;
import com.groupsix.projecttracker.repository.EffortRepository;
import com.groupsix.projecttracker.repository.ProjectRepository;
import com.groupsix.projecttracker.repository.RequirementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EffortService {

    private final EffortRepository effortRepository;
    private final ProjectRepository projectRepository;
    private final RequirementRepository requirementRepository;

    public List<EffortDto> getAllEffortsByProject(UUID projectId) {
        return effortRepository.findByProjectId(projectId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public EffortDto createEffort(EffortDto effortDto) {
        Project project = projectRepository.findById(effortDto.getProjectId())
                .orElseThrow(NoSuchElementException::new);

        EffortEntity effort = new EffortEntity();
        effort.setId(UUID.randomUUID());
        effort.setMemberName(effortDto.getMemberName());
        effort.setCategory(effortDto.getCategory());
        effort.setHours(effortDto.getHours());
        effort.setEntryType(effortDto.getEntryType());
        effort.setEntryDate(effortDto.getEntryDate());
        effort.setProject(project);
        if (effortDto.getRequirementId() != null) {
            Requirement req = requirementRepository.findById(effortDto.getRequirementId()).orElse(null);
            effort.setRequirement(req);
        }

        EffortEntity savedEffort = effortRepository.save(effort);
        return toDto(savedEffort);
    }

    public EffortDto editEffort(UUID effortId, EffortDto effortDto) {
        EffortEntity effort = effortRepository.findById(effortId)
                .orElseThrow(NoSuchElementException::new);

        Project project = projectRepository.findById(effortDto.getProjectId())
                .orElseThrow(NoSuchElementException::new);

        effort.setMemberName(effortDto.getMemberName());
        effort.setCategory(effortDto.getCategory());
        effort.setHours(effortDto.getHours());
        effort.setEntryType(effortDto.getEntryType());
        effort.setEntryDate(effortDto.getEntryDate());
        effort.setProject(project);
        if (effortDto.getRequirementId() != null) {
            Requirement req = requirementRepository.findById(effortDto.getRequirementId()).orElse(null);
            effort.setRequirement(req);
        } else {
            effort.setRequirement(null);
        }

        EffortEntity updatedEffort = effortRepository.save(effort);
        return toDto(updatedEffort);
    }

    public void deleteEffort(UUID effortId) {
        EffortEntity effort = effortRepository.findById(effortId)
                .orElseThrow(NoSuchElementException::new);

        effortRepository.delete(effort);
    }

    public double getTotalProjectHours(UUID projectId) {
        return effortRepository.findByProjectId(projectId)
                .stream()
                .mapToDouble(EffortEntity::getHours)
                .sum();
    }

    public Map<String, Double> getProjectEffortSummary(UUID projectId) {
        return effortRepository.findByProjectId(projectId)
                .stream()
                .collect(Collectors.groupingBy(
                        EffortEntity::getCategory,
                        Collectors.summingDouble(EffortEntity::getHours)
                ));
    }

    private EffortDto toDto(EffortEntity effort) {
        return EffortDto.builder()
                .id(effort.getId())
                .memberName(effort.getMemberName())
                .category(effort.getCategory())
                .hours(effort.getHours())
                .entryType(effort.getEntryType())
                .entryDate(effort.getEntryDate())
                .projectId(effort.getProject().getId())
                .requirementId(effort.getRequirement() != null ? effort.getRequirement().getId() : null)
                .build();
    }
}