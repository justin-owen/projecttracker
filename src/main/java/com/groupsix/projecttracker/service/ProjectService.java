package com.groupsix.projecttracker.service;

import com.groupsix.projecttracker.dto.ProjectDto;
import com.groupsix.projecttracker.dto.ProjectRequestDto;
import com.groupsix.projecttracker.entity.Project;
import com.groupsix.projecttracker.mapper.ProjectMapper;
import com.groupsix.projecttracker.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public List<ProjectDto> listAllProjects(){
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::toDto)
                .toList();
    }

    public ProjectDto createProject(ProjectRequestDto projectBody){
        Project project = new Project();
        project.setId(UUID.randomUUID());
        project.setName(projectBody.getName());
        project.setDescription(projectBody.getDescription());
        project.setOwner(projectBody.getOwner());
        project.setCreatedAt(Instant.now());
        project.setUpdatedAt(Instant.now());
        project.setMembers(projectBody.getMembers());

        try {
            Project savedProject = projectRepository.save(project);
            return projectMapper.toDto(savedProject);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Creating Project Failed.");
        }
    }
}
