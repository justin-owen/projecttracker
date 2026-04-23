package com.groupsix.projecttracker.mapper;

import com.groupsix.projecttracker.dto.ProjectDto;
import com.groupsix.projecttracker.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {
    public ProjectDto toDto(Project project){
        if (project == null) return null;

        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .owner(project.getOwner())
                .description(project.getDescription())
                .members(project.getMembers())
                .build();
    }
}
