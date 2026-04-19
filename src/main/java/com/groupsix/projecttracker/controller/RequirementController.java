package com.groupsix.projecttracker.controller;

import com.groupsix.projecttracker.dto.RequirementDto;
import com.groupsix.projecttracker.service.RequirementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/requirements")
@RequiredArgsConstructor
public class RequirementController {

    private final RequirementService requirementService;

    @GetMapping("/{projectId}")
    public ResponseEntity<List<RequirementDto>> getAllRequirementsByProject(
            @PathVariable UUID projectId){
        return ResponseEntity.ok(requirementService.listAllRequirementsByProject(projectId));
    }
}
