package com.groupsix.projecttracker.controller;

import com.groupsix.projecttracker.dto.ProjectDto;
import com.groupsix.projecttracker.dto.ProjectRequestDto;
import com.groupsix.projecttracker.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/")
    public ResponseEntity<List<ProjectDto>> getAllProjects(){
        return ResponseEntity.ok(projectService.listAllProjects());
    }

    @PostMapping("/create")
    public ResponseEntity<ProjectDto> createProject(
            @RequestBody ProjectRequestDto project){
        try {
            ProjectDto createdProject = projectService.createProject(project);
            return ResponseEntity.ok(createdProject);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }
    }
}
