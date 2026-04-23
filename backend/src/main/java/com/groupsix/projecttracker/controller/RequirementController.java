package com.groupsix.projecttracker.controller;

import com.groupsix.projecttracker.dto.RequirementDto;
import com.groupsix.projecttracker.dto.RequirementRequestDto;
import com.groupsix.projecttracker.service.RequirementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
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

    @PostMapping("/create")
    public ResponseEntity<RequirementDto> createRequirement(
            @RequestBody RequirementRequestDto requirementRequest){
        try {
            RequirementDto requirement = requirementService.createRequirement(requirementRequest);
            return ResponseEntity.ok(requirement);
        } catch (IllegalArgumentException | NoSuchElementException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{requirementId}")
    public ResponseEntity<RequirementDto> editRequirement(
            @PathVariable UUID requirementId,
            @RequestBody RequirementRequestDto requirementRequest
    ){
        try {
            RequirementDto requirement = requirementService.editRequirement(requirementId, requirementRequest);
            return ResponseEntity.ok(requirement);
        } catch (IllegalArgumentException | NoSuchElementException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{requirementId}")
    public ResponseEntity<RequirementDto> deleteRequirement(
            @PathVariable UUID requirementId
    ){
        try {
            requirementService.deleteRequirement(requirementId);
            return ResponseEntity.ok().body(null);
        } catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(null);
        }
    }
}
