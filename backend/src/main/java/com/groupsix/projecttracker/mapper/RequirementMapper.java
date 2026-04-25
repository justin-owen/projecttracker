package com.groupsix.projecttracker.mapper;

import com.groupsix.projecttracker.dto.RequirementDto;
import com.groupsix.projecttracker.entity.Requirement;
import org.springframework.stereotype.Component;

@Component
public class RequirementMapper {
    public RequirementDto toDto(Requirement requirement){
        if (requirement == null) return null;

        return RequirementDto.builder()
                .id(requirement.getId())
                .type(requirement.getType())
                .requirement(requirement.getRequirement())
                .build();
    }
}
