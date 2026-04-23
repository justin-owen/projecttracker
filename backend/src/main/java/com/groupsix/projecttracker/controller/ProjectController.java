package com.groupsix.projecttracker.controller;

import com.groupsix.projecttracker.dto.ProjectDto;
import com.groupsix.projecttracker.dto.ProjectRequestDto;
import com.groupsix.projecttracker.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.Map;
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

    //Edit Project
    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDto> editProject(
            @PathVariable UUID projectId,
            @RequestBody ProjectRequestDto projectBody) {

        try {
            ProjectDto updatedProject = projectService.editProject(projectId, projectBody);
            return ResponseEntity.ok(updatedProject);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    //Delete Project
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable UUID projectId) {
        try {
            projectService.deleteProject(projectId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    //Add Member
    @PostMapping("/{projectId}/members")
    public ResponseEntity<ProjectDto> addMember(
            @PathVariable UUID projectId,
            @RequestBody Map<String, String> body) {

        try {
            String memberName = body.get("name");
            ProjectDto updatedProject = projectService.addMember(projectId, memberName);
            return ResponseEntity.ok(updatedProject);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    //Delete Member
    @DeleteMapping("/{projectId}/members")
    public ResponseEntity<ProjectDto> deleteMember(
            @PathVariable UUID projectId,
            @RequestBody Map<String, String> body) {

        try {
            String memberName = body.get("name");
            ProjectDto updatedProject = projectService.deleteMember(projectId, memberName);
            return ResponseEntity.ok(updatedProject);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    //Edit Member
    @PutMapping("/{projectId}/members")
    public ResponseEntity<ProjectDto> editMember(
            @PathVariable UUID projectId,
            @RequestBody Map<String, String> body) {

        try {
            String oldName = body.get("oldName");
            String newName = body.get("newName");

            ProjectDto updatedProject = projectService.editMember(projectId, oldName, newName);
            return ResponseEntity.ok(updatedProject);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
