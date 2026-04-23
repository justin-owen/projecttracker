package com.groupsix.projecttracker.service;

import com.groupsix.projecttracker.dto.ProjectDto;
import com.groupsix.projecttracker.dto.ProjectRequestDto;
import com.groupsix.projecttracker.entity.Project;
import com.groupsix.projecttracker.mapper.ProjectMapper;
import com.groupsix.projecttracker.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    //Find Project By ID
    public Project getProjectByID(UUID projectId){
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project Not Found."));
    }

    //Edit Project
    public ProjectDto editProject(UUID projectId, ProjectRequestDto projectBody) {
        Project project = getProjectByID(projectId);

        project.setName(projectBody.getName());
        project.setDescription(projectBody.getDescription());
        project.setOwner(projectBody.getOwner());
        project.setUpdatedAt(Instant.now());

        Project updatedProject = projectRepository.save(project);

        return projectMapper.toDto(updatedProject);
    }

    //Delete Project
    public void deleteProject(UUID projectId) {
        Project project = getProjectByID(projectId);
        projectRepository.delete(project);
    }

    //Add Member
    public ProjectDto addMember(UUID projectId, String memberName)
    {
        Project project = getProjectByID(projectId);

        List<String> members = project.getMembers();

        if (members == null)
        {
            members = new ArrayList<>();
        }

        members.add(memberName);
        project.setMembers(members);
        project.setUpdatedAt(Instant.now());

        Project updatedProject = projectRepository.save(project);

        return projectMapper.toDto(updatedProject);
    }

    //Delete Member
    public ProjectDto deleteMember(UUID projectId, String memberName) {
        Project project = getProjectByID(projectId);

        List<String> members = project.getMembers();

        if(members == null || members.isEmpty()) {
            throw new RuntimeException("No members found for this project");
        }

        boolean removed = members.remove(memberName);

        if(!removed) {
            throw new RuntimeException("Member not found");
        }

        project.setMembers(members);
        project.setUpdatedAt(Instant.now());

        Project updatedProject = projectRepository.save(project);

        return projectMapper.toDto(updatedProject);
    }

    //Edit Member
    public ProjectDto editMember(UUID projectId, String oldName, String newName) {
        Project project = getProjectByID(projectId);

        List<String> members = project.getMembers();

        if (members == null || members.isEmpty()) {
            throw new RuntimeException("No members found for this project");
        }

        int index = members.indexOf(oldName);

        if(index == -1) {
            throw new RuntimeException("Member not found");
        }

        members.set(index, newName);
        project.setMembers(members);
        project.setUpdatedAt(Instant.now());

        Project updatedProject = projectRepository.save(project);

        return projectMapper.toDto(updatedProject);
    }

}
